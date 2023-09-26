package hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.payment.PaymentVendor

interface PaymentVendorCaller{
    fun support(paymentVendor: PaymentVendor): Boolean
    fun pay(request: PaymentProcessor.PaymentRequest): String
}
