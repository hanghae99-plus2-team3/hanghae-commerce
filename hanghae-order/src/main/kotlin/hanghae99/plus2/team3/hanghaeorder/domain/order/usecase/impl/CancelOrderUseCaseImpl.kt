package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.impl

import hanghae99.plus2.team3.hanghaeorder.domain.order.service.OrderService
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.CancelOrderUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.payment.service.PaymentService

class CancelOrderUseCaseImpl(
    private val orderService: OrderService,
    private val paymentService: PaymentService
) : CancelOrderUseCase {
    override fun command(command: CancelOrderUseCase.Command): String {
        val cancelableOrder = orderService.getCancelableOrder(command.orderNum, command.userId)
        paymentService.requestRefundOf(cancelableOrder)
        return cancelableOrder.order.orderNum
    }

}
