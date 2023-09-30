package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentVendor
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.PaymentVendorCaller
import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment

class FakeKakaoErrorPaymentVendorCaller : PaymentVendorCaller {
    override fun support(paymentVendor: PaymentVendor): Boolean {
        return paymentVendor == PaymentVendor.KAKAO
    }

    override fun pay(request: PaymentProcessor.PaymentRequest): Payment {
        return Payment.createSuccessPayment(
            paymentNum = request.paymentNum,
            paymentVendor = request.paymentVendor,
            paymentAmount = request.paymentAmount,
        )
    }
}
