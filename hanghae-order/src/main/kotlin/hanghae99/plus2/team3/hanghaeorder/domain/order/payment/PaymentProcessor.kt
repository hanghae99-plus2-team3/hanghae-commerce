package hanghae99.plus2.team3.hanghaeorder.domain.order.payment

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.PaymentVendorCaller
import hanghae99.plus2.team3.hanghaeorder.exception.NotSupportedPaymentVendorException
import org.springframework.stereotype.Component

@Component
class PaymentProcessor(
    private val paymentVendorCallers: List<PaymentVendorCaller>
) {
    fun pay(request: PaymentRequest) {
        findRequestedPaymentVendor(request.paymentVendor)
            .pay(request)
    }

    private fun findRequestedPaymentVendor(requestedPaymentVendor: PaymentVendor) =
        paymentVendorCallers.find { it.support(requestedPaymentVendor) }
            ?: throw NotSupportedPaymentVendorException()

    data class PaymentRequest(
        val orderNum: String,
        val paymentVendor: PaymentVendor,
        val paymentAmount: Long,
    )
}