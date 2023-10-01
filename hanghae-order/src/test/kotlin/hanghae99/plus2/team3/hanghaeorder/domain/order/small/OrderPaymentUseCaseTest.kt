package hanghae99.plus2.team3.hanghaeorder.domain.order.small

import hanghae99.plus2.team3.hanghaeorder.common.exception.*
import hanghae99.plus2.team3.hanghaeorder.domain.order.DeliveryInfo
import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor.ProductInfo
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.mock.*
import hanghae99.plus2.team3.hanghaeorder.domain.order.service.OrderService
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.impl.OrderPaymentUseCaseImpl
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.OrderItemValidator
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.OrderStatusValidator
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.PaymentRequestUserValidator
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.PaymentTotalValidator
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentResultCode
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentVendor
import hanghae99.plus2.team3.hanghaeorder.domain.payment.service.PaymentService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * OrderPaymentUseCaseTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/24
 */
class OrderPaymentUseCaseTest {
    private lateinit var sut: OrderPaymentUseCase
    private lateinit var orderRepository: OrderRepository
    private lateinit var orderItemRepository: OrderItemRepository
    private lateinit var productsAccessor: ProductsAccessor
    private lateinit var userAccessor: UserInfoAccessor
    private val paymentRepository: FakePaymentRepositoryImpl = FakePaymentRepositoryImpl()

    @BeforeEach
    fun setUp() {
        prepareTest()
        sut = OrderPaymentUseCaseImpl(
            OrderService(
                orderRepository,
                orderItemRepository,
                productsAccessor,
                listOf(
                    PaymentTotalValidator(),
                    OrderStatusValidator(),
                    PaymentRequestUserValidator(),
                    OrderItemValidator()
                ),
                PaymentProcessor(listOf(FakeTossErrorPaymentVendorCaller(), FakeKakaoErrorPaymentVendorCaller()))
            ),
            PaymentService(paymentRepository)
        )
    }

    @Test
    fun `정상적으로 주문한 내역의 결제 요청을 하면 기대하는 응답(성공)을 반환한다`() {

        val paymentId = sut.command(
            OrderPaymentUseCase.Command(
                orderNum = "orderNum-1",
                userId = 1L,
                paymentVendor = PaymentVendor.KAKAO,
                paymentAmount = 10000L,
            )
        )
        assertThat(paymentId).isNotNull
    }

    @Test
    fun `주문한 내역이 없는 경우 결제 요청을 하면 기대하는 응답(실패)을 반환한다`() {
        assertThatThrownBy {
            sut.command(
                OrderPaymentUseCase.Command(
                    orderNum = "orderNum-999",
                    userId = 1L,
                    paymentVendor = PaymentVendor.TOSS,
                    paymentAmount = 5000L,
                )
            )
        }.isInstanceOf(OrderNotFoundException::class.java)
            .hasMessage(ErrorCode.ORDER_NOT_FOUND.message)
    }

    @Test
    fun `주문자의 정보와 결제 요청자의 정보가 다를 때 결제하면 기대하는 응답(실패)을 반환한다`() {
        assertThatThrownBy {
            sut.command(
                OrderPaymentUseCase.Command(
                    orderNum = "orderNum-1",
                    userId = 2L,
                    paymentVendor = PaymentVendor.TOSS,
                    paymentAmount = 10000L,
                )
            )
        }.isInstanceOf(OrderInfoNotValidException::class.java)
            .hasMessage(ErrorCode.ORDER_INFO_NOT_VALID.message)
    }

    @Test
    fun `주문 금액과 결제 금액이 다를 때 결제를 요청하면 기대하는 응답(실패)을 반환한다`() {
        assertThatThrownBy {
            sut.command(
                OrderPaymentUseCase.Command(
                    orderNum = "orderNum-1",
                    userId = 1L,
                    paymentVendor = PaymentVendor.TOSS,
                    paymentAmount = 5000L,
                )
            )
        }.isInstanceOf(OrderedPriceNotMatchException::class.java)
            .hasMessage(ErrorCode.ORDER_PRICE_NOT_MATCH.message)
    }

    @Test
    fun `이미 결제 완료한 주문에 대해서 다시 결제 요청하면 기대하는 응답(실패)을 반환한다`() {
        assertThatThrownBy {
            sut.command(
                OrderPaymentUseCase.Command(
                    orderNum = "orderNum-4",
                    userId = 1L,
                    paymentVendor = PaymentVendor.TOSS,
                    paymentAmount = 6000L,
                )
            )
        }.isInstanceOf(OrderAlreadyPayedException::class.java)
            .hasMessage(ErrorCode.ORDER_ALREADY_PAYED.message)
    }

    @Test
    fun `외부 결제사에게 보낸 결제 요청이 일정시간 이상 소요되면 기대하는 응답(실패)을 반환한다`() {
        assertThatThrownBy {
            sut.command(
                OrderPaymentUseCase.Command(
                    orderNum = "orderNum-1",
                    userId = 1L,
                    paymentVendor = PaymentVendor.TOSS,
                    paymentAmount = 10000L,
                )
            )
        }.isInstanceOf(PaymentProcessException::class.java)
            .hasMessage(ErrorCode.ERROR_ACCRUED_WHEN_PROCESSING_PAYMENT.message)
    }

    @Test
    fun `지원하지 않는 외부 결제사로 결제를 요청하면 기대하는 응답(실패)을 반환한다`() {
        assertThatThrownBy {
            sut.command(
                OrderPaymentUseCase.Command(
                    orderNum = "orderNum-1",
                    userId = 1L,
                    paymentVendor = PaymentVendor.NAVER,
                    paymentAmount = 10000L,
                )
            )
        }.isInstanceOf(PaymentProcessException::class.java)
            .hasMessage(ErrorCode.ERROR_ACCRUED_WHEN_PROCESSING_PAYMENT.message)
    }

    @Test
    fun `결제 요청시 오류가 발생하면 차감했던 재고를 원복한다`() {
        assertThatThrownBy {
            sut.command(
                OrderPaymentUseCase.Command(
                    orderNum = "orderNum-1",
                    userId = 1L,
                    paymentVendor = PaymentVendor.TOSS,
                    paymentAmount = 10000L,
                )
            )
        }.isInstanceOf(PaymentProcessException::class.java)
            .hasMessage(ErrorCode.ERROR_ACCRUED_WHEN_PROCESSING_PAYMENT.message)

        val orderItems = orderItemRepository.findByOrderId(1L)
        assertThat(orderItems[0].quantity).isEqualTo(5)
    }

    @Test
    fun `결제요청 발생시 요청 데이터를 결제 로그 테이블에 저장 한다`() {
        val paymentId = sut.command(
            OrderPaymentUseCase.Command(
                orderNum = "orderNum-1",
                userId = 1L,
                paymentVendor = PaymentVendor.KAKAO,
                paymentAmount = 10000L,
            )
        )
        assertThat(paymentId).isNotNull

        val paymentRequests = paymentRepository.paymentRequests
        assertThat(paymentRequests.size).isEqualTo(1)
        assertThat(paymentRequests[0].paymentNum).isEqualTo("PAYMENT-orderNum-1")
        assertThat(paymentRequests[0].paymentVendor).isEqualTo(PaymentVendor.KAKAO)
        assertThat(paymentRequests[0].paymentAmount).isEqualTo(10000L)
        assertThat(paymentRequests[0].success).isEqualTo(true)
    }

    @Test
    fun `결제요청 실패시 요청 데이터를 결제 로그와 결과 코드를 테이블에 저장 한다`() {
        assertThatThrownBy {
            sut.command(
                OrderPaymentUseCase.Command(
                    orderNum = "orderNum-1",
                    userId = 1L,
                    paymentVendor = PaymentVendor.TOSS,
                    paymentAmount = 10000L,
                )
            )
        }.isInstanceOf(PaymentProcessException::class.java)
            .hasMessage(ErrorCode.ERROR_ACCRUED_WHEN_PROCESSING_PAYMENT.message)


        val paymentRequests = paymentRepository.paymentRequests
        assertThat(paymentRequests.size).isEqualTo(1)
        assertThat(paymentRequests[0].paymentNum).isEqualTo("PAYMENT-orderNum-1")
        assertThat(paymentRequests[0].paymentVendor).isEqualTo(PaymentVendor.KAKAO)
        assertThat(paymentRequests[0].paymentAmount).isEqualTo(10000L)
        assertThat(paymentRequests[0].success).isEqualTo(true)
        assertThat(paymentRequests[0].paymentResultCode).isEqualTo(PaymentResultCode.NOT_SUPPORTED_PAYMENT_VENDOR)
    }





    private fun prepareTest() {
        val users = listOf(
            UserInfoAccessor.UserInfo(userId = 1L, userName = "홍길동", userEmail = "test@gmail.com"),
            UserInfoAccessor.UserInfo(userId = 2L, userName = "임꺽정", userEmail = "test2@gmail.com"),
        )
        val productsInStock = mutableListOf(
            ProductInfo(productId = 1L, productName = "상품1", productPrice = 2000L, productStock = 5),
            ProductInfo(productId = 2L, productName = "상품2", productPrice = 3000L, productStock = 5),
        )
        val orders = listOf(
            Order(
                1L, "orderNum-1", 1L,
                DeliveryInfo("홍길동", "010-1234-5678", "13254", "서울시 강남구", "123-456"),
                Order.OrderStatus.ORDERED
            ), Order(
                2L, "orderNum-3", 1L,
                DeliveryInfo("홍길동", "010-1234-5678", "13254", "서울시 강남구", "123-456"),
                Order.OrderStatus.ORDERED
            ), Order(
                3L, "orderNum-4", 1L,
                DeliveryInfo("홍길동", "010-1234-5678", "13254", "서울시 강남구", "123-456"),
                Order.OrderStatus.PAYMENT_COMPLETED
            )
        )
        val orderItems = listOf(
            OrderItem(1L, orders[0], 1L, 5, 2000L, OrderItem.DeliveryStatus.BEFORE_PAYMENT),
            OrderItem(2L, orders[1], 2L, 6, 3000L, OrderItem.DeliveryStatus.BEFORE_PAYMENT),
            OrderItem(3L, orders[2], 2L, 2, 3000L, OrderItem.DeliveryStatus.READY),

            )

        orderRepository = FakeOrderRepositoryImpl(orders)
        orderItemRepository = FakeOrderItemRepositoryImpl(orderItems)
        productsAccessor = FakeProductsAccessor(productsInStock)
        userAccessor = FakeUserInfoAccessor(users)
    }

}

