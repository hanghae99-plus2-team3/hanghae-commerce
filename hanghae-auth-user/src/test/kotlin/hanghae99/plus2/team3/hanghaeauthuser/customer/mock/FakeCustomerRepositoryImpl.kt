package hanghae99.plus2.team3.hanghaeauthuser.customer.mock

import hanghae99.plus2.team3.hanghaeauthuser.customer.domain.CustomerEntity
import hanghae99.plus2.team3.hanghaeauthuser.customer.repository.CustomerRepository
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.query.FluentQuery
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import java.util.function.Function

class FakeCustomerRepositoryImpl(
    preRegisterCustomers: List<CustomerEntity>
): CustomerRepository {
    private val customers: MutableList<CustomerEntity> = Collections.synchronizedList(mutableListOf<CustomerEntity>())

    init {
        preRegisterCustomers.forEach { save(it) }
    }

    override fun save(customerEntity: CustomerEntity): Long {
        customers.add(customerEntity)
        return customerEntity.pk
    }

    override fun existsByLoginId(loginId: String): Boolean {
        return customers.any { it.loginId == loginId }
    }

    override fun findByLoginIdAndPassword(loginId: String, password: String): CustomerEntity? {
        return customers.firstOrNull { it.loginId == loginId && it.password == password }
    }
}
