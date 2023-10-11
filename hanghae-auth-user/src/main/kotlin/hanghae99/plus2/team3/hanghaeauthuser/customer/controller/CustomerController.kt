package hanghae99.plus2.team3.hanghaeauthuser.customer.controller

import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.CustomerLoginRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.CustomerRegisterRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.service.CustomerAuthService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/vl/customer")
class CustomerController(
    private val customerAuthService: CustomerAuthService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun registerCustomer(
        @RequestBody customerRegisterRequest: CustomerRegisterRequest
    ) {
        customerAuthService.createCustomer(customerRegisterRequest)
    }

    @PostMapping("/login")
    fun loginCustomer(
        @RequestBody customerLoginRequest: CustomerLoginRequest
    ) {
        customerAuthService.login(customerLoginRequest)
    }
}
