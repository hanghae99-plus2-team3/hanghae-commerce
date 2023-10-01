package hanghae99.plus2.team3.hanghaeorder.domain.payment.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment

/**
 * PaymentRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/10/01
 */
interface PaymentRepository {
    fun save(payment: Payment): Payment
}
