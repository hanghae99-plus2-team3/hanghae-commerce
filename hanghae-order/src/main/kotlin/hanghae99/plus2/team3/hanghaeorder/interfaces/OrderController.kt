package hanghae99.plus2.team3.hanghaeorder.interfaces

import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.RegisterOrderUseCase
import hanghae99.plus2.team3.hanghaeorder.interfaces.request.OrderProductsRequest
import hanghae99.plus2.team3.hanghaeorder.interfaces.request.toCommand
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * OrderController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/23
 */

@RestController
@RequestMapping("/v1/orders")
class OrderController(
    private val registerOrderUseCase: RegisterOrderUseCase
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun registerOrder(
        @RequestBody orderProductsRequest: OrderProductsRequest
    ) {
        registerOrderUseCase.command(orderProductsRequest.toCommand())
    }
}


