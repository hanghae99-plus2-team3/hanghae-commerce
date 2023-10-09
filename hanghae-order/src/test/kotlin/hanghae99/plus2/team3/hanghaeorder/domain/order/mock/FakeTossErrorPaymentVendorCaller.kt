package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.common.exception.PaymentTimeOutException
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.PaymentVendorCaller
import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentVendor

class FakeTossErrorPaymentVendorCaller : PaymentVendorCaller {
    override fun support(paymentVendor: PaymentVendor): Boolean {
        return paymentVendor == PaymentVendor.TOSS
    }

    override fun pay(request: PaymentProcessor.PaymentRequest): Payment {
        throw PaymentTimeOutException()
    }

    override fun refund(request: PaymentProcessor.RefundRequest): Payment {
        throw PaymentTimeOutException()
    }
}
