package hanghae99.plus2.team3.hanghaeauthuser.customer.service

import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.CustomerOrderRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.repository.CustomerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CustomerOrderServiceTest {

    private lateinit var sut: CustomerOrderService
    private lateinit var customerRepository: CustomerRepository

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
}
