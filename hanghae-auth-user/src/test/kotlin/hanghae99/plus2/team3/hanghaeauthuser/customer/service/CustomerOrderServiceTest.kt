package hanghae99.plus2.team3.hanghaeauthuser.customer.service

import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.CustomerOrderRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.domain.CustomerEntity
import hanghae99.plus2.team3.hanghaeauthuser.customer.mock.FakeCustomerRepositoryImpl
import hanghae99.plus2.team3.hanghaeauthuser.customer.repository.CustomerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CustomerOrderServiceTest {

    private lateinit var sut: CustomerOrderService
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setUp() {
        prepareTest()
    }

    @Test
    fun `회원이 주문에 성공한다`() {
        val orderId = sut.createOrder(
            createCustomerOrderRequest(1L)
        )

        assertThat(orderId).isNotNull
    }

    private fun createCustomerOrderRequest(productId: Long): CustomerOrderRequest {
        return CustomerOrderRequest(
            "testName",
            "01001234567",
            "01040",
            "test1",
            "test2",
            "",
            productId
        )
    }

    private fun prepareTest() {
        val customerEntities = listOf(
            CustomerEntity(1L, "testId11", "testPassword123", "정찬우"),
            CustomerEntity(2L, "testId22", "testPassword1234", "홍길동")
        )

        customerRepository = FakeCustomerRepositoryImpl(customerEntities)
        sut = CustomerOrderService(customerRepository)
    }
}
