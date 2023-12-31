package hanghae99.plus2.team3.hanghaeauthuser.customer.repository.impl

import hanghae99.plus2.team3.hanghaeauthuser.customer.domain.CustomerEntity
import hanghae99.plus2.team3.hanghaeauthuser.customer.repository.CustomerRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class CustomerRepositoryImpl(
    private val customerJpaRepository: CustomerJpaRepository
) : CustomerRepository {

    override fun save(customerEntity: CustomerEntity): Long {
        return customerJpaRepository.save(customerEntity).pk
    }

    override fun findById(pk: Long): CustomerEntity {
        return customerJpaRepository.findById(pk).orElseThrow()
    }
}

interface CustomerJpaRepository : JpaRepository<CustomerEntity, Long>
