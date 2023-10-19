package hanghae99.plus2.team3.hanghaeorder.domain.payment.service

import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment
import hanghae99.plus2.team3.hanghaeorder.domain.payment.infrastructure.PaymentRepository
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * PaymentEventListener
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/17/23
 */

@Component
class PaymentEventListener(
    private val paymentRepository: PaymentRepository
) {

    @Async("paymentRequestLog")
    @EventListener
    @Transactional
    fun saveSuccessPaymentRequestLog(payment: Payment) {
        Thread.sleep(10000)
        paymentRepository.save(payment)
        println("========================================================")
    }
}
