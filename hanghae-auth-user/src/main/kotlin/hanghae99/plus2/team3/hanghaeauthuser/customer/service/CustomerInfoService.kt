package hanghae99.plus2.team3.hanghaeauthuser.customer.service

import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.CustomerRegisterRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.toEntity
import hanghae99.plus2.team3.hanghaeauthuser.customer.exception.AlreadyExistCustomerException
import hanghae99.plus2.team3.hanghaeauthuser.customer.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerInfoService(
    private val customerRepository: CustomerRepository
) {
    fun createCustomerInfo(customerRegisterRequest: CustomerRegisterRequest): Long {

        return customerRepository.save(customerRegisterRequest.toEntity())
    }
}
