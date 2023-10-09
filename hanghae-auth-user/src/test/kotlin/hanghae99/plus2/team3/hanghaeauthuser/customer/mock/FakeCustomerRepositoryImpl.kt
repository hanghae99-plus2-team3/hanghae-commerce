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

    override fun <S : CustomerEntity> save(entity: S): S {
        customers.add(entity)
        return entity
    }

    override fun existsByLoginId(loginId: String): Boolean {

    }

    override fun findByLoginIdAndPassword(loginId: String, password: String): CustomerEntity {
        TODO("Not yet implemented")
    }

    override fun <S : CustomerEntity?> saveAll(entities: MutableIterable<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun <S : CustomerEntity?> findAll(example: Example<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun <S : CustomerEntity?> findAll(example: Example<S>, sort: Sort): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun findAll(): MutableList<CustomerEntity> {
        TODO("Not yet implemented")
    }

    override fun findAll(sort: Sort): MutableList<CustomerEntity> {
        TODO("Not yet implemented")
    }

    override fun findAll(pageable: Pageable): Page<CustomerEntity> {
        TODO("Not yet implemented")
    }

    override fun <S : CustomerEntity?> findAll(example: Example<S>, pageable: Pageable): Page<S> {
        TODO("Not yet implemented")
    }

    override fun findAllById(ids: MutableIterable<Long>): MutableList<CustomerEntity> {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }

    override fun <S : CustomerEntity?> count(example: Example<S>): Long {
        TODO("Not yet implemented")
    }

    override fun delete(entity: CustomerEntity) {
        TODO("Not yet implemented")
    }

    override fun deleteAllById(ids: MutableIterable<Long>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll(entities: MutableIterable<CustomerEntity>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun <S : CustomerEntity?> findOne(example: Example<S>): Optional<S> {
        TODO("Not yet implemented")
    }

    override fun <S : CustomerEntity?> exists(example: Example<S>): Boolean {
        TODO("Not yet implemented")
    }

    override fun <S : CustomerEntity?, R : Any?> findBy(
        example: Example<S>,
        queryFunction: Function<FluentQuery.FetchableFluentQuery<S>, R>
    ): R {
        TODO("Not yet implemented")
    }

    override fun flush() {
        TODO("Not yet implemented")
    }

    override fun <S : CustomerEntity?> saveAndFlush(entity: S): S {
        TODO("Not yet implemented")
    }

    override fun <S : CustomerEntity?> saveAllAndFlush(entities: MutableIterable<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun deleteAllInBatch(entities: MutableIterable<CustomerEntity>) {
        TODO("Not yet implemented")
    }

    override fun deleteAllInBatch() {
        TODO("Not yet implemented")
    }

    override fun deleteAllByIdInBatch(ids: MutableIterable<Long>) {
        TODO("Not yet implemented")
    }

    override fun getReferenceById(id: Long): CustomerEntity {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): CustomerEntity {
        TODO("Not yet implemented")
    }

    override fun getOne(id: Long): CustomerEntity {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun existsById(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Optional<CustomerEntity> {
        TODO("Not yet implemented")
    }
}
