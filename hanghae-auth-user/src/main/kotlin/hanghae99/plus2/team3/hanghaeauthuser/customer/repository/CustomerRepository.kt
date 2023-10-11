package hanghae99.plus2.team3.hanghaeauthuser.customer.repository

import hanghae99.plus2.team3.hanghaeauthuser.customer.domain.CustomerEntity

interface CustomerRepository {
    fun existsByLoginId(loginId: String): Boolean
    fun findByLoginIdAndPassword(loginId: String, password: String): CustomerEntity

    fun save(customerEntity: CustomerEntity): Long
}
