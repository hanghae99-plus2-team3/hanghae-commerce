package hanghae99.plus2.team3.hanghaeauthuser.customer.service

import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.CustomerRegisterRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.toEntity
import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.response.CustomerInfoResponse
import hanghae99.plus2.team3.hanghaeauthuser.customer.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerInfoService(
    private val customerRepository: CustomerRepository
) {
    fun createCustomer(customerRegisterRequest: CustomerRegisterRequest): Long {
        return customerRepository.save(customerRegisterRequest.toEntity())
    }

    fun findCustomer(pk: Long): CustomerInfoResponse {
        val entity = customerRepository.findById(pk)

        return CustomerInfoResponse.create(entity)
    }
}
