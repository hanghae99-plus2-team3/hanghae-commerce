package hanghae99.plus2.team3.hanghaeauthuser.customer.repository

import hanghae99.plus2.team3.hanghaeauthuser.customer.domain.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<CustomerEntity, Long> {
    fun existsByLoginId(loginId: String): Boolean

    fun findByLoginIdAndPassword(loginId: String, password: String): CustomerEntity
}
