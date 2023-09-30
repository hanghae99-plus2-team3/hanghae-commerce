package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.impl

import hanghae99.plus2.team3.hanghaeorder.domain.order.service.OrderService
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase

class OrderPaymentUseCaseImpl(
    private val orderService: OrderService
) : OrderPaymentUseCase {

    override fun command(command: OrderPaymentUseCase.Command): String {
        return orderService.makePaymentForOder(command)
    }
}
