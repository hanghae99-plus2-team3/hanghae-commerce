package hanghae99.plus2.team3.hanghaeauthuser.customer.repository.impl

import hanghae99.plus2.team3.hanghaeauthuser.customer.domain.CustomerEntity
import hanghae99.plus2.team3.hanghaeauthuser.customer.repository.CustomerRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class CustomerRepositoryImpl(
    private val customerJpaRepository: CustomerJpaRepository
) : CustomerRepository {
    override fun existsByLoginId(loginId: String): Boolean {
        return customerJpaRepository.findByLoginId(loginId)
    }

    override fun findByLoginIdAndPassword(loginId: String, password: String): CustomerEntity {
        return findByLoginIdAndPassword(loginId, password)
    }

    override fun save(customerEntity: CustomerEntity): Long {
        return customerJpaRepository.save(customerEntity).pk
    }
}

interface CustomerJpaRepository : JpaRepository<CustomerEntity, Long> {
    fun findByLoginId(loginId: String): Boolean

    fun findByLoginIdAndPassword(loginId: String, password: String): CustomerEntity
}
