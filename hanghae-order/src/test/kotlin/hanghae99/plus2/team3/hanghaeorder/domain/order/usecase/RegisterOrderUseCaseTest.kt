package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * RegisterOrderUseCaseTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/21
 */
class RegisterOrderUseCaseTest {
    private lateinit var sut: RegisterOrderUseCase

    @BeforeEach
    fun setUp() {
        sut = RegisterOrderUseCaseImpl()
    }

    @Test
    fun `정상적으로 상품을 주문하면 기대하는 응답(성공)을 반환한다`() {

        val command = RegisterOrderUseCase.Command(
            userId = 1L,
            orderItemList = listOf(
                RegisterOrderUseCase.Command.OrderItem(
                    productId = 1L,
                    quantity = 2,
                    productPrice = 1000L,
                ),
                RegisterOrderUseCase.Command.OrderItem(
                    productId = 2L,
                    quantity = 5,
                    productPrice = 5000L,
                ),
            )
        )
        val orderNum = sut.command(command)

        assertThat(orderNum).isNotEmpty()
    }
}

class RegisterOrderUseCaseImpl : RegisterOrderUseCase {
    override fun command(command: RegisterOrderUseCase.Command) : String{
        TODO("Not yet implemented")
    }

}


interface RegisterOrderUseCase {

    fun command(command:Command) : String

    data class Command(
        val userId: Long,
        val orderItemList: List<OrderItem>,
    ){
        data class OrderItem(
            val productId: Long,
            val quantity: Int,
            val productPrice: Long,
        )
    }


}
