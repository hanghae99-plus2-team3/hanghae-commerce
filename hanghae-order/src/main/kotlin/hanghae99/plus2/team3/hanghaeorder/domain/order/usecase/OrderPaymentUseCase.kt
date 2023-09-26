package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase

import hanghae99.plus2.team3.hanghaeorder.domain.order.payment.PaymentVendor

interface OrderPaymentUseCase {
    fun command(command: Command): String

    data class Command(
        val orderNum: String,
        val userId: Long,
        val paymentVendor: PaymentVendor,
        val paymentAmount: Long,
    )
}
