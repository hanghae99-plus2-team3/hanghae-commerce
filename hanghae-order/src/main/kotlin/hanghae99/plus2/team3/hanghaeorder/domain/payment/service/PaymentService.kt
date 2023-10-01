package hanghae99.plus2.team3.hanghaeorder.domain.payment.service

import hanghae99.plus2.team3.hanghaeorder.common.exception.PaymentProcessException
import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment
import hanghae99.plus2.team3.hanghaeorder.domain.payment.infrastructure.PaymentRepository
import org.springframework.stereotype.Service

/**
 * PaymentService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/10/01
 */
@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
) {
    fun savePaymentRequestLog(payment: Payment) {
        paymentRepository.save(payment)
        if(! payment.success)
            throw PaymentProcessException(payment.paymentResultCode)
    }
}
