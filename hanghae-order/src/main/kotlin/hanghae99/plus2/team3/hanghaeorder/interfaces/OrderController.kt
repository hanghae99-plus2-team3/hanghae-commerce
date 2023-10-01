package hanghae99.plus2.team3.hanghaeorder.interfaces

import hanghae99.plus2.team3.hanghaeorder.common.CurrentUser
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.RegisterOrderUseCase
import hanghae99.plus2.team3.hanghaeorder.interfaces.request.OrderPaymentRequest
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
    private val registerOrderUseCase: RegisterOrderUseCase,
    private val orderPaymentUseCase: OrderPaymentUseCase,
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun registerOrder(
        @RequestBody orderProductsRequest: OrderProductsRequest,
        currentUser: CurrentUser
    ) {
        registerOrderUseCase.command(orderProductsRequest.toCommand(currentUser.userId))
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/payment")
    fun makePaymentForOrder(
        @RequestBody orderPaymentRequest: OrderPaymentRequest,
        currentUser: CurrentUser
    ) {
        orderPaymentUseCase.command(orderPaymentRequest.toCommand(currentUser.userId))
    }
}
