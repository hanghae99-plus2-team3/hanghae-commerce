package hanghae99.plus2.team3.hanghaeauthuser.customer.service

import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.CustomerLoginRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.CustomerRegisterRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.toEntity
import hanghae99.plus2.team3.hanghaeauthuser.customer.exception.AlreadyExistCustomerException
import hanghae99.plus2.team3.hanghaeauthuser.customer.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerAuthService(
    private val customerRepository: CustomerRepository
) {
    fun createCustomer(customerRegisterRequest: CustomerRegisterRequest): Long {
        if (customerRepository.existsByLoginId(customerRegisterRequest.loginId)) {
            throw AlreadyExistCustomerException()
        }

        return customerRepository.save(customerRegisterRequest.toEntity(0L))
    }

    fun login(customerLoginRequest: CustomerLoginRequest): Boolean {
        val findCustomer = customerRepository.findByLoginIdAndPassword(
            customerLoginRequest.loginId,
            customerLoginRequest.password
        )

        return findCustomer?.let {
            true
        } ?: throw IllegalArgumentException("해당 로그인 정보를 찾을 수 없습니다.")
    }
}
