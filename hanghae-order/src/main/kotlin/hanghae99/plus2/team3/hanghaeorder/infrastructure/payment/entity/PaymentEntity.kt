package hanghae99.plus2.team3.hanghaeorder.infrastructure.payment.entity

import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment
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
    val paymentNum: String,
    @Enumerated(EnumType.STRING)
    val paymentVendor: PaymentVendor,
    val paymentAmount: Long,
    val success: Boolean,
){

    fun toDomain() =
        Payment(
            id = id,
            paymentNum = paymentNum,
            paymentVendor = paymentVendor,
            paymentAmount = paymentAmount,
            success = success,
        )

    companion object {
        fun of(
            payment: Payment
        ): PaymentEntity {
            return PaymentEntity(
                id = payment.id,
                paymentNum = payment.paymentNum,
                paymentVendor = payment.paymentVendor,
                paymentAmount = payment.paymentAmount,
                success = payment.success,
            )
        }
    }
}

