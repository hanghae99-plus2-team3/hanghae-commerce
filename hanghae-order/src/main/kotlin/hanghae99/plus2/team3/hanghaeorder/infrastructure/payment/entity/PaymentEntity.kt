package hanghae99.plus2.team3.hanghaeorder.infrastructure.payment.entity

import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentResultCode
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentVendor
import jakarta.persistence.*

/**
 * PaymentEntity
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/10/01
 */
@Entity
class PaymentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val orderNum: String,
    @Enumerated(EnumType.STRING)
    val paymentVendor: PaymentVendor,
    val paymentAmount: Long,
    val success: Boolean,
    val paymentResultCode: PaymentResultCode
) {

    fun toDomain() =
        Payment(
            id = id,
            orderNum = orderNum,
            paymentVendor = paymentVendor,
            paymentAmount = paymentAmount,
            success = success,
            paymentResultCode = paymentResultCode
        )

    companion object {
        fun of(
            payment: Payment
        ): PaymentEntity {
            return PaymentEntity(
                id = payment.id,
                orderNum = payment.orderNum,
                paymentVendor = payment.paymentVendor,
                paymentAmount = payment.paymentAmount,
                success = payment.success,
                paymentResultCode = payment.paymentResultCode
            )
        }
    }
}
