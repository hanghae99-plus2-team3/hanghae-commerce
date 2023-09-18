package hanghae99.plus2.team3.commerce.jaehyun.seller.interfaces

import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.usecase.RegisterSellerUseCase
import hanghae99.plus2.team3.commerce.jaehyun.seller.interfaces.request.SellerRegistrationRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * SellerController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/18
 */

@RestController
@RequestMapping("/v1/seller")
class SellerController(
    private val registerSellerUseCase: RegisterSellerUseCase
) {

    @PostMapping
    fun registerSeller(
        @RequestBody request: SellerRegistrationRequest
    ):ResponseEntity<Unit>{
        registerSellerUseCase.command(
            RegisterSellerUseCase.Command(
                name = request.name
            )
        )
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }
}
