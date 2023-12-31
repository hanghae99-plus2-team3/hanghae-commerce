package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.impl

import hanghae99.plus2.team3.hanghaeorder.domain.order.service.OrderService
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.payment.service.PaymentAsyncService
import org.springframework.stereotype.Component

@Component
class OrderPaymentUseCaseImpl(
    private val orderService: OrderService,
    private val paymentService: PaymentAsyncService
) : OrderPaymentUseCase {

    override fun command(command: OrderPaymentUseCase.Command): String {
        val orderWithItems = orderService.getOrderForPayment(command.orderNum, command.userId)
        paymentService.requestPaymentOf(orderWithItems, command)
        return command.orderNum
    }
}
