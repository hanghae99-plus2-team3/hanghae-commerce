package hanghae99.plus2.team3.hanghaeorder.interfaces.request

import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentVendor

data class OrderPaymentRequest(
    val orderNum: String,
    val paymentVendor: PaymentVendor,
    val paymentAmount: Long
)

fun OrderPaymentRequest.toCommand(userId: Long): OrderPaymentUseCase.Command {
    return OrderPaymentUseCase.Command(
        orderNum = orderNum,
        userId = userId,
        paymentVendor = paymentVendor,
        paymentAmount = paymentAmount
    )
}
