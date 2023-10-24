package hanghae99.plus2.team3.hanghaeorder.domain.payment.infrastructure

import hanghae99.plus2.team3.hanghaeorder.common.task.PaymentTask

/**
 * PaymentRequestTaskProducer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */
interface PaymentRequestTaskProducer {
    fun produce(task: PaymentTask)
}
