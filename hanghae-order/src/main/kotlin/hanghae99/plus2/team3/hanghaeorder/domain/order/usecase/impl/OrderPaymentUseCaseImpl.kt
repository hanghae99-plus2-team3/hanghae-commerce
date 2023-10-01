package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.impl

import hanghae99.plus2.team3.hanghaeorder.domain.payment.service.PaymentService
import hanghae99.plus2.team3.hanghaeorder.domain.order.service.OrderService
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase

class OrderPaymentUseCaseImpl(
    private val orderService: OrderService,
    private val paymentService: PaymentService
) : OrderPaymentUseCase {

    override fun command(command: OrderPaymentUseCase.Command): String {
        val payment = orderService.makePaymentForOder(command)
        paymentService.savePaymentRequestLog(payment)
        return payment.paymentNum
    }
}
