package hanghae99.plus2.team3.hanghaeauthuser.customer.service

import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.CustomerOrderRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerOrderService(
    private val customerRepository: CustomerRepository
) {

    fun createOrder(customerOrderRequest: CustomerOrderRequest): Long {
        return 1L
    }
}
