package hanghae99.plus2.team3.hanghaeorder.domain.order.small

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * CancelOrderUseCaseTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/10/03
 */
class CancelOrderUseCaseTest {

    private lateinit var sut: CancelOrderUseCase

    @BeforeEach
    fun setUp() {
        sut = CancelOrderUseCaseImpl()
    }

    @Test
    fun `정상적으로 주문 취소 요청을 하면 기대하는 응답(성공)을 반환한다`() {
        val orderNum = "orderNum-1"
        val userId = 1L
        val result = sut.command(
            CancelOrderUseCase.Command(
                orderNum,
                userId,
            )
        )
        assertThat(result).isEqualTo(orderNum)
    }

}

class CancelOrderUseCaseImpl : CancelOrderUseCase {
    override fun command(command: CancelOrderUseCase.Command): String {
        TODO("Not yet implemented")
    }

}

interface CancelOrderUseCase {
    fun command(command: Command): String

    data class Command(
        val orderNum: String,
        val userId: Long,

        )
}
