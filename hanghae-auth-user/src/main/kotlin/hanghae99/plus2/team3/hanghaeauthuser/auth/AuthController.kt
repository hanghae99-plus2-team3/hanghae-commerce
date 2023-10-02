package hanghae99.plus2.team3.hanghaeauthuser.auth

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController {

    private var memberPkIndex: Long = 1

    @PostMapping("/register")
    fun registerMember(
        @RequestBody registerMemberRequest: RegisterMemberRequest
    ): RegisterMemberResponse {
        return RegisterMemberResponse(memberPkIndex.toString())
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest
    ): LoginResponse {
        return LoginResponse(memberPkIndex.toString())
    }

    @GetMapping("/info")
    fun getTokenInfo(
        @RequestHeader(value = "authorization") authorization: String
    ): AuthTokenInfoResponse {
        return AuthTokenInfoResponse(authorization.toLong())
    }
}
