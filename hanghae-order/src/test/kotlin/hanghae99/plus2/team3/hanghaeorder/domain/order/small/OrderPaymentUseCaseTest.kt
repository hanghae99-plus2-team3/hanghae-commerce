package hanghae99.plus2.team3.hanghaeorder.domain.order.small

import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.RegisterOrderUseCase
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * OrderPaymentUseCaseTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/24
 */
class OrderPaymentUseCaseTest {

    @Test
    fun `정상적으로 주문한 내역의 결제 요청을 하면 기대하는 응답(성공)을 반환한다`() {

        val paymentId = OrderPaymentUseCase.command(
            Command(
                orderId = 1L,
                userId = 1L,
                paymentType = PaymentType.CARD,
                paymentAmount = 5000L,
            )
        )

        assertThat(paymentId).isNotNull
    }
}


