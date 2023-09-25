package hanghae99.plus2.team3.hanghaeorder.domain.order.small

import hanghae99.plus2.team3.hanghaeorder.domain.order.DeliveryInfo
import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.QueryUserInfoByApi
import hanghae99.plus2.team3.hanghaeorder.domain.order.mock.FakeOrderItemRepositoryImpl
import hanghae99.plus2.team3.hanghaeorder.domain.order.mock.FakeOrderRepositoryImpl
import hanghae99.plus2.team3.hanghaeorder.domain.order.mock.FakeQueryUserInfoByApiImpl
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.RegisterOrderUseCase
import hanghae99.plus2.team3.hanghaeorder.exception.*
import org.assertj.core.api.Assertions
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
        val orderRepository = FakeOrderRepositoryImpl()
        val orderItemRepository = FakeOrderItemRepositoryImpl()
        sut = OrderPaymentUseCaseImpl(orderRepository, orderItemRepository, FakeQueryUserInfoByApiImpl(users))

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
        orderRepository.save(
            order
        )

        orderItemRepository.save(
            OrderItem(
                1L,
                order,
                1L,
                10,
                2000L,
                OrderItem.DeliveryStatus.READY
            )
        )
    }

    @Test
    fun `정상적으로 주문한 내역의 결제 요청을 하면 기대하는 응답(성공)을 반환한다`() {

        val paymentId = sut.command(
            OrderPaymentUseCase.Command(
                orderId = 1L,
                userId = 1L,
                paymentType = PaymentType.CARD,
                paymentAmount = 20000L,
            )
        )
        assertThat(paymentId).isNotNull
    }

    @Test
    fun `주문한 내역이 없는 경우 결제 요청을 하면 기대하는 응답(실패)을 반환한다`() {
        assertThatThrownBy {
            sut.command(
                OrderPaymentUseCase.Command(
                    orderId = 999L,
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
                    orderId = 1L,
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
                    orderId = 1L,
                    userId = 1L,
                    paymentType = PaymentType.CARD,
                    paymentAmount = 5000L,
                )
            )
        }.isInstanceOf(OrderedPriceNotMatchException::class.java)
            .hasMessage(ErrorCode.ORDER_PRICE_NOT_MATCH.message)
    }
}

interface OrderPaymentUseCase {
    fun command(command: Command): String

    data class Command(
        val orderId: Long,
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
) : OrderPaymentUseCase {

    companion object {
        private const val PAYMENT_PREFIX = "PAYMENT"
    }

    override fun command(command: OrderPaymentUseCase.Command): String {
        if (queryUserInfoByApi.query(command.userId) == null)
            throw OrderedUserNotFoundException()

        val order = (orderRepository.findByIdOrNull(command.orderId) ?:
        throw OrderNotFoundException())

        if( order.userId != command.userId)
            throw OrderInfoNotValidException()

        if(orderItemRepository.findByOrderId(order.id).sumOf { it.productPrice * it.quantity } != command.paymentAmount)
            throw OrderedPriceNotMatchException()

        return PAYMENT_PREFIX + command.orderId
    }
}
