package hanghae99.plus2.team3.hanghaeorder.domain.order.small

import hanghae99.plus2.team3.hanghaeorder.common.exception.CanNotCancelOrderException
import hanghae99.plus2.team3.hanghaeorder.common.exception.ErrorCode
import hanghae99.plus2.team3.hanghaeorder.common.exception.OrderNotFoundException
import hanghae99.plus2.team3.hanghaeorder.domain.order.DeliveryInfo
import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.mock.*
import hanghae99.plus2.team3.hanghaeorder.domain.order.service.OrderService
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.CancelOrderUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.impl.CancelOrderUseCaseImpl
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.OrderItemValidator
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.OrderStatusValidator
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.PaymentRequestUserValidator
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.PaymentTotalValidator
import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentResultCode
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentVendor
import hanghae99.plus2.team3.hanghaeorder.domain.payment.infrastructure.PaymentRepository
import hanghae99.plus2.team3.hanghaeorder.domain.payment.service.PaymentService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher

/**
 * CancelOrderUseCaseTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/10/03
 */
class CancelOrderUseCaseTest {

    private lateinit var sut: CancelOrderUseCase
    private lateinit var orderRepository: OrderRepository
    private lateinit var orderItemRepository: OrderItemRepository
    private lateinit var productsAccessor: ProductsAccessor
    private lateinit var paymentRepository: PaymentRepository

    @BeforeEach
    fun setUp() {
        prepareTest()
        sut = CancelOrderUseCaseImpl(
            OrderService(
                orderRepository,
                orderItemRepository,
                productsAccessor
            ),
            PaymentService(
                paymentRepository,
                orderRepository,
                orderItemRepository,
                productsAccessor,
                listOf(
                    PaymentTotalValidator(),
                    OrderStatusValidator(),
                    PaymentRequestUserValidator(),
                    OrderItemValidator()
                ),
                PaymentProcessor(listOf(FakeTossErrorPaymentVendorCaller(), FakeKakaoPaymentVendorCaller())),
                ApplicationEventPublisher { }
            )
        )
    }

    @Test
    fun `정상적으로 주문 취소 요청을 하면 기대하는 응답(성공)을 반환한다`() {
        val orderNum = "orderNum-1"
        val userId = 1L
        val result = sut.command(
            CancelOrderUseCase.Command(
                orderNum,
                userId
            )
        )
        assertThat(result).isEqualTo(orderNum)
    }

    @Test
    fun `존재 하지 않는 주문을 취소하면 기대하는 응답(실패)을 반환한다`() {
        val orderNum = "orderNum-99"
        val userId = 1L
        assertThatThrownBy {
            sut.command(
                CancelOrderUseCase.Command(
                    orderNum,
                    userId
                )
            )
        }.isInstanceOf(OrderNotFoundException::class.java)
            .hasMessage(ErrorCode.ORDER_NOT_FOUND.message)
    }

    @Test
    fun `주문자의 정보와 취소 요청자의 정보가 다를 때 주문을 취소하면 기대하는 응답(실패)을 반환한다`() {
        val orderNum = "orderNum-1"
        val userId = 99L
        assertThatThrownBy {
            sut.command(
                CancelOrderUseCase.Command(
                    orderNum,
                    userId
                )
            )
        }.isInstanceOf(OrderNotFoundException::class.java)
            .hasMessage(ErrorCode.ORDER_NOT_FOUND.message)
    }

    @Test
    fun `주문의 상태가 배송 중 이후일 때 주문을 취소하면 기대하는 응답(실패)을 반환한다`() {
        val orderNum = "orderNum-2"
        val userId = 2L
        assertThatThrownBy {
            sut.command(
                CancelOrderUseCase.Command(
                    orderNum,
                    userId
                )
            )
        }.isInstanceOf(CanNotCancelOrderException::class.java)
            .hasMessage(ErrorCode.ORDER_PRODUCT_STARTED_DELIVERY.message)
    }

    @Test
    fun `이미 결제된 취소 가능한 주문을 취소하면 결제를 취소한다`() {
        val orderNum = "orderNum-1"
        val userId = 1L

        val result = sut.command(
            CancelOrderUseCase.Command(
                orderNum,
                userId
            )
        )

        assertThat(result).isEqualTo(orderNum)
        val refundPayment = paymentRepository.getByOrderNum(orderNum)
        assertThat(refundPayment).isNotNull
        assertThat(refundPayment.paymentResultCode).isEqualTo(PaymentResultCode.REFUND_SUCCESS)
    }

    private fun prepareTest() {
        val users = listOf(
            UserInfoAccessor.UserInfo(pk = 1L, name = "홍길동", email = "test@gmail.com"),
            UserInfoAccessor.UserInfo(pk = 2L, name = "임꺽정", email = "test2@gmail.com")
        )
        val productsInStock = mutableListOf(
            ProductsAccessor.ProductInfo(
                productId = 1L,
                productName = "상품1",
                productPrice = 2000L,
                productStock = 5
            ),
            ProductsAccessor.ProductInfo(
                productId = 2L,
                productName = "상품2",
                productPrice = 3000L,
                productStock = 5
            )
        )
        val orders = listOf(
            Order(
                1L,
                "orderNum-1",
                1L,
                DeliveryInfo("홍길동", "010-1234-5678", "13254", "서울시 강남구", "123-456"),
                Order.OrderStatus.ORDERED
            ),
            Order(
                2L,
                "orderNum-2",
                2L,
                DeliveryInfo("홍길동", "010-1234-5678", "13254", "서울시 강남구", "123-456"),
                Order.OrderStatus.DELIVERY
            ),
            Order(
                3L,
                "orderNum-4",
                1L,
                DeliveryInfo("홍길동", "010-1234-5678", "13254", "서울시 강남구", "123-456"),
                Order.OrderStatus.PAYMENT_COMPLETED
            )
        )
        val orderItems = listOf(
            OrderItem(1L, orders[0], 1L, 5, 2000L, OrderItem.DeliveryStatus.BEFORE_PAYMENT),
            OrderItem(2L, orders[1], 2L, 6, 3000L, OrderItem.DeliveryStatus.BEFORE_PAYMENT),
            OrderItem(3L, orders[2], 2L, 2, 3000L, OrderItem.DeliveryStatus.READY)

        )

        val payments = listOf(
            Payment(
                1L,
                "orderNum-1",
                PaymentVendor.KAKAO,
                10000L,
                true,
                PaymentResultCode.PAYMENT_SUCCESS
            ),
            Payment(
                2L,
                "orderNum-3",
                PaymentVendor.KAKAO,
                10000L,
                true,
                PaymentResultCode.PAYMENT_SUCCESS
            )
        )

        paymentRepository = FakePaymentRepositoryImpl(payments)
        orderRepository = FakeOrderRepositoryImpl(orders)
        orderItemRepository = FakeOrderItemRepositoryImpl(orderItems)
        productsAccessor = FakeProductsAccessor(productsInStock)
    }
}
