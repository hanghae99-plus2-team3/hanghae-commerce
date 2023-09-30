package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.order.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.payment.PaymentVendor
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.PaymentVendorCaller
import hanghae99.plus2.team3.hanghaeorder.common.exception.PaymentProcessException

class FakeTossErrorPaymentVendorCaller : PaymentVendorCaller {
    override fun support(paymentVendor: PaymentVendor): Boolean {
        return paymentVendor == PaymentVendor.TOSS
    }

    override fun pay(request: PaymentProcessor.PaymentRequest): String {
        throw PaymentProcessException()
    }
}
