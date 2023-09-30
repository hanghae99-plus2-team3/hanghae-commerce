package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.impl

import hanghae99.plus2.team3.hanghaeorder.domain.order.service.OrderService
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.RegisterOrderUseCase
import org.springframework.stereotype.Component

@Component
class RegisterOrderUseCaseImpl(
    private val orderService: OrderService,
) : RegisterOrderUseCase {

    override fun command(command: RegisterOrderUseCase.Command): String {
        return orderService.makeOrder(command)
    }

}
