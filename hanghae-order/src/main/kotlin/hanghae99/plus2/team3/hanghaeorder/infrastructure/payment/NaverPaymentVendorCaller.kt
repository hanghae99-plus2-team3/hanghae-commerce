package hanghae99.plus2.team3.hanghaeorder.infrastructure.payment

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.PaymentVendorCaller
import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentVendor
import org.springframework.stereotype.Component

@Component
class NaverPaymentVendorCaller : PaymentVendorCaller {
    override fun support(paymentVendor: PaymentVendor): Boolean {
        return paymentVendor == PaymentVendor.NAVER
    }

    override fun pay(request: PaymentProcessor.PaymentRequest): Payment {
        return Payment.createSuccessPayment(
            orderNum = request.paymentNum,
            paymentVendor = request.paymentVendor,
            paymentAmount = request.paymentAmount
        )
    }

    override fun refund(request: PaymentProcessor.RefundRequest): Payment {
        return Payment.createSuccessRefund(
            id = request.payment.id,
            orderNum = request.payment.orderNum,
            paymentVendor = request.payment.paymentVendor,
            paymentAmount = request.payment.paymentAmount
        )
    }
}
