package hanghae99.plus2.team3.hanghaeauthuser.auth

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController {

    @GetMapping("/info")
    fun getTokenInfo(
        @RequestHeader(value = "authorization") authorization: Long
    ): AuthTokenInfoResponse {
        return AuthTokenInfoResponse(authorization)
    }
}
