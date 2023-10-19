package hanghae99.plus2.team3.hanghaeauthuser.customer.mock

import hanghae99.plus2.team3.hanghaeauthuser.customer.domain.CustomerEntity
import hanghae99.plus2.team3.hanghaeauthuser.customer.repository.CustomerRepository
import java.util.*

class FakeCustomerRepositoryImpl(
    preRegisterCustomers: List<CustomerEntity>
) : CustomerRepository {
    private val customers: MutableList<CustomerEntity> = Collections.synchronizedList(mutableListOf<CustomerEntity>())

    init {
        preRegisterCustomers.forEach { save(it) }
    }

    override fun save(customerEntity: CustomerEntity): Long {
        customers.add(customerEntity)
        return customerEntity.pk
    }
}
