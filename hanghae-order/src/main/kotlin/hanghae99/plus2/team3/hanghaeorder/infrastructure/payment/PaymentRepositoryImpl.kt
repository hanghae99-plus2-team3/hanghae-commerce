package hanghae99.plus2.team3.hanghaeorder.infrastructure.payment

import hanghae99.plus2.team3.hanghaeorder.common.exception.PaymentNotFoundException
import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment
import hanghae99.plus2.team3.hanghaeorder.domain.payment.infrastructure.PaymentRepository
import hanghae99.plus2.team3.hanghaeorder.infrastructure.payment.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * PaymentRepositoryImpl
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/10/01
 */

@Repository
class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository
) : PaymentRepository {
    override fun save(payment: Payment): Payment {
        return paymentJpaRepository.save(PaymentEntity.of(payment)).toDomain()
    }

    override fun getByOrderNum(orderNum: String): Payment {
        return (paymentJpaRepository.findByOrderNum(orderNum) ?: throw PaymentNotFoundException()).toDomain()
    }
}

interface PaymentJpaRepository : JpaRepository<PaymentEntity, Long> {
    fun findByOrderNum(orderNum: String): PaymentEntity?
}
