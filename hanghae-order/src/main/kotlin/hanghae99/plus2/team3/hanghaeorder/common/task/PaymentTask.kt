package hanghae99.plus2.team3.hanghaeorder.common.task

import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentVendor

/**
 * PaymentTask
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */
data class PaymentTask(
    val taskId: String,
    val taskName: String = "PAYMENT-TASK",
    val userId: Long,
    val reduceStockSubTaskList: List<ReduceStockSubTask>,
    val paymentNum: String,
    val paymentVendor: PaymentVendor,
    val paymentAmount: Long
)
