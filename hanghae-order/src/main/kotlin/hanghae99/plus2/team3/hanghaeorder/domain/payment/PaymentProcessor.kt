package hanghae99.plus2.team3.hanghaeorder.domain.payment

import hanghae99.plus2.team3.hanghaeorder.common.exception.NotSupportedPaymentVendorException
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.PaymentVendorCaller
import org.springframework.stereotype.Component

@Component
class PaymentProcessor(
    private val paymentVendorCallers: List<PaymentVendorCaller>
) {
    fun pay(request: PaymentRequest): Payment {
        return findRequestedPaymentVendor(request.paymentVendor)
            .pay(request)
    }

    fun refund(request: RefundRequest): Payment {
        return findRequestedPaymentVendor(request.payment.paymentVendor)
            .refund(request)
    }

    private fun findRequestedPaymentVendor(requestedPaymentVendor: PaymentVendor) =
        paymentVendorCallers.find { it.support(requestedPaymentVendor) }
            ?: throw NotSupportedPaymentVendorException()

    data class PaymentRequest(
        val paymentNum: String,
        val paymentVendor: PaymentVendor,
        val paymentAmount: Long
    )

    data class RefundRequest(
        val payment: Payment
    )
}
