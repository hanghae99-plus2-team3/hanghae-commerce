package hanghae99.plus2.team3.hanghaeorder.domain.order.small

import hanghae99.plus2.team3.hanghaeorder.domain.order.DeliveryInfo
import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
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

    @BeforeEach
    fun setUp() {
        val users = listOf(
            QueryUserInfoByApi.UserInfo(
                userId = 1L,
                userName = "홍길동",
                userEmail = "test@gmail.com"
            ),
            QueryUserInfoByApi.UserInfo(
                userId = 2L,
                userName = "임꺽정",
                userEmail = "test2@gmail.com"
            )
        )
        val productsInStock = mutableListOf(
            ProductsAccessor.ProductInfo(
                productId = 1L,
                productName = "상품1",
                productPrice = 2000L,
                productStock = 5,
            ),
            ProductsAccessor.ProductInfo(
                productId = 2L,
                productName = "상품2",
                productPrice = 3000L,
                productStock = 5,
            ),
        )
        val orderRepository = FakeOrderRepositoryImpl()
        val orderItemRepository = FakeOrderItemRepositoryImpl()
        val productsAccessor = FakeProductsAccessorImpl(productsInStock)
        val userAccessor = FakeQueryUserInfoByApiImpl(users)
        sut = OrderPaymentUseCaseImpl(
            orderRepository,
            orderItemRepository,
            userAccessor,
            productsAccessor
        )

        val order = Order(
            1L,
            "orderNum-1",
            1L,
            DeliveryInfo(
                "홍길동",
                "010-1234-5678",
                "13254",
                "서울시 강남구",
                "123-456"
            ),
            Order.OrderStatus.ORDERED
        )
        val invalidOrder = Order(
            2L,
            "orderNum-3",
            1L,
            DeliveryInfo(
                "홍길동",
                "010-1234-5678",
                "13254",
                "서울시 강남구",
                "123-456"
            ),
            Order.OrderStatus.ORDERED
        )
        orderRepository.save(
            order
        )
        orderRepository.save(
            invalidOrder
        )

        orderItemRepository.save(
            OrderItem(
                1L,
                order,
                1L,
                5,
                2000L,
                OrderItem.DeliveryStatus.READY
            )
        )
        orderItemRepository.save(
            OrderItem(
                2L,
                invalidOrder,
                2L,
                6,
                3000L,
                OrderItem.DeliveryStatus.READY
            )
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
                    paymentAmount = 5000L,
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
    fun `결제시 주문한 상품의 제고가 부족하면 기대하는 응답(실패)을 반환한다`() {
        assertThatThrownBy {
            sut.command(
                OrderPaymentUseCase.Command(
                    orderNum = "orderNum-3",
                    userId = 1L,
                    paymentType = PaymentType.CARD,
                    paymentAmount = 18000L,
                )
            )
        }.isInstanceOf(OrderedItemOutOfStockException::class.java)
            .hasMessage(ErrorCode.ORDERED_ITEM_OUT_OF_STOCK.message )
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
) : OrderPaymentUseCase {

    companion object {
        private const val PAYMENT_PREFIX = "PAYMENT"
    }

    override fun command(command: OrderPaymentUseCase.Command): String {
        if (queryUserInfoByApi.query(command.userId) == null)
            throw OrderedUserNotFoundException()

        val order = (orderRepository.findByOrderNum(command.orderNum) ?: throw OrderNotFoundException())
        val orderItems = orderItemRepository.findByOrderId(order.id)

        if (orderItems.isEmpty())
            throw OrderInfoNotValidException()

        if (order.userId != command.userId)
            throw OrderInfoNotValidException()

        if (orderItems.sumOf { it.productPrice * it.quantity } != command.paymentAmount)
            throw OrderedPriceNotMatchException()

        productsAccessor.updateProductStock(orderItems.map {
            ProductsAccessor.UpdateProductStockRequest(it.productId, it.quantity)
        })

        return PAYMENT_PREFIX + order.id
    }
}
