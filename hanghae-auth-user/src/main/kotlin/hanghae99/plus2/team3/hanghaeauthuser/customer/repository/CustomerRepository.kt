package hanghae99.plus2.team3.hanghaeauthuser.customer.repository

import hanghae99.plus2.team3.hanghaeauthuser.customer.domain.CustomerEntity

interface CustomerRepository {

    fun save(customerEntity: CustomerEntity): Long

    fun findById(pk: Long): CustomerEntity
}
