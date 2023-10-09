package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.impl

import hanghae99.plus2.team3.hanghaeorder.domain.order.service.OrderService
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.payment.service.PaymentService
import org.springframework.stereotype.Component

@Component
class OrderPaymentUseCaseImpl(
    private val orderService: OrderService,
    private val paymentService: PaymentService
) : OrderPaymentUseCase {

    override fun command(command: OrderPaymentUseCase.Command): String {
////        val payment = orderService.makePaymentForOder(command)
        val orderWithItems = orderService.getOrderWithOrderItems(command.orderNum)
        val paymentResult = paymentService.requestPaymentOf(orderWithItems, command)
        paymentService.savePaymentRequestLog(paymentResult)
        return command.orderNum
    }
}
