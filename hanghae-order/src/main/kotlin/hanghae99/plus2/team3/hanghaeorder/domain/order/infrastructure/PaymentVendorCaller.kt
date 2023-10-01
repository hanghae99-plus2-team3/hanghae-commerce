package hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentVendor

interface PaymentVendorCaller{
    fun support(paymentVendor: PaymentVendor): Boolean
    fun pay(request: PaymentProcessor.PaymentRequest): Payment
}
