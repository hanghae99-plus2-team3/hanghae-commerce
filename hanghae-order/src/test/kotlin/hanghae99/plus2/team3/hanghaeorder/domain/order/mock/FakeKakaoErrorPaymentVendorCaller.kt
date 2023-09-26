package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.order.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.payment.PaymentVendor
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.PaymentVendorCaller

class FakeKakaoErrorPaymentVendorCaller : PaymentVendorCaller {
    override fun support(paymentVendor: PaymentVendor): Boolean {
        return paymentVendor == PaymentVendor.KAKAO
    }

    override fun pay(request: PaymentProcessor.PaymentRequest): String {
        return request.orderNum
    }
}
