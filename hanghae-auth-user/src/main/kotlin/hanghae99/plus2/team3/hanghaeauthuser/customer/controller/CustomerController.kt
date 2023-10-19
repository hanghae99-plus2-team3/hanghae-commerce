package hanghae99.plus2.team3.hanghaeauthuser.customer.controller

import hanghae99.plus2.team3.hanghaeauthuser.auth.AuthServiceUseCase
import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.CustomerRegisterRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.toAuthRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.service.CustomerInfoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customer")
class CustomerController(
    private val customerInfoService: CustomerInfoService,
    private val authService: AuthServiceUseCase
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun registerCustomer(
        @RequestBody customerRegisterRequest: CustomerRegisterRequest
    ) {
        authService.register(customerRegisterRequest.toAuthRequest())
        customerInfoService.createCustomerInfo(customerRegisterRequest)
    }
}
