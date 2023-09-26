package hanghae99.plus2.team3.hanghaeorder.domain.order.small

import hanghae99.plus2.team3.hanghaeorder.domain.order.DeliveryInfo
import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor.*
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.QueryUserInfoByApi
import hanghae99.plus2.team3.hanghaeorder.domain.order.mock.*
import hanghae99.plus2.team3.hanghaeorder.exception.*
import org.assertj.core.api.Assertions.*
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
    private lateinit var userAccessor: QueryUserInfoByApi

    @BeforeEach
    fun setUp() {
        prepareTest()
        sut = OrderPaymentUseCaseImpl(
            orderRepository,
            orderItemRepository,
            userAccessor,
            productsAccessor,
            listOf(PaymentTotalValidator(), OrderStatusValidator(), PaymentRequestUserValidator(), OrderItemValidator())
        )
    }

    @Test
    fun `정상적으로 주문한 내역의 결제 요청을 하면 기대하는 응답(성공)을 반환한다`() {

        val paymentId = sut.command(
            OrderPaymentUseCase.Command(
                orderNum = "orderNum-1",
                userId = 1L,
                paymentType = PaymentType.CARD,
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
                    paymentType = PaymentType.CARD,
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
                    paymentType = PaymentType.CARD,
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
                    paymentType = PaymentType.CARD,
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
                    paymentType = PaymentType.CARD,
                    paymentAmount = 6000L,
                )
            )
        }.isInstanceOf(OrderAlreadyPayedException::class.java)
            .hasMessage(ErrorCode.ORDER_ALREADY_PAYED.message)
    }

    private fun prepareTest() {
        val users = listOf(
            QueryUserInfoByApi.UserInfo(userId = 1L, userName = "홍길동", userEmail = "test@gmail.com"),
            QueryUserInfoByApi.UserInfo(userId = 2L, userName = "임꺽정", userEmail = "test2@gmail.com"),
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
        productsAccessor = FakeProductsAccessorImpl(productsInStock)
        userAccessor = FakeQueryUserInfoByApiImpl(users)
    }

}

interface OrderPaymentUseCase {
    fun command(command: Command): String

    data class Command(
        val orderNum: String,
        val userId: Long,
        val paymentType: PaymentType,
        val paymentAmount: Long,
    )
}

enum class PaymentType {
    CARD,
}

class OrderPaymentUseCaseImpl(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val queryUserInfoByApi: QueryUserInfoByApi,
    private val productsAccessor: ProductsAccessor,
    private val paymentValidator: List<PaymentValidator>,
) : OrderPaymentUseCase {

    companion object {
        private const val PAYMENT_PREFIX = "PAYMENT"
    }

    override fun command(command: OrderPaymentUseCase.Command): String {
        if (queryUserInfoByApi.query(command.userId) == null)
            throw OrderedUserNotFoundException()

        val order = (orderRepository.findByOrderNum(command.orderNum) ?: throw OrderNotFoundException())
        val orderItems = orderItemRepository.findByOrderId(order.id)

        paymentValidator.forEach { it.validate(order, orderItems, command) }

        productsAccessor.updateProductStock(orderItems.map {
            UpdateProductStockRequest(it.productId, it.quantity)
        })

        return PAYMENT_PREFIX + order.id
    }
}

interface PaymentValidator {
    fun validate(order: Order, orderItems: List<OrderItem>, command: OrderPaymentUseCase.Command)
}

class PaymentTotalValidator : PaymentValidator {
    override fun validate(order: Order, orderItems: List<OrderItem>, command: OrderPaymentUseCase.Command) {
        if (orderItems.sumOf { it.productPrice * it.quantity } != command.paymentAmount)
            throw OrderedPriceNotMatchException()
    }
}

class OrderStatusValidator : PaymentValidator {
    override fun validate(order: Order, orderItems: List<OrderItem>, command: OrderPaymentUseCase.Command) {
        if (order.isPaymentCompleted())
            throw OrderAlreadyPayedException()
    }
}

class PaymentRequestUserValidator:PaymentValidator{
    override fun validate(order: Order, orderItems: List<OrderItem>, command: OrderPaymentUseCase.Command) {
        if (order.userId != command.userId)
            throw OrderInfoNotValidException()
    }
}

class OrderItemValidator : PaymentValidator {
    override fun validate(order: Order, orderItems: List<OrderItem>, command: OrderPaymentUseCase.Command) {
        if (orderItems.isEmpty())
            throw OrderInfoNotValidException()
    }
}
