package hanghae99.plus2.team3.hanghaeauthuser.auth

import org.springframework.stereotype.Service

interface AuthServiceUseCase {
    fun register(request: RegisterMemberRequest): RegisterMemberResponse
    fun login(request: LoginRequest): LoginResponse
    fun getTokenInfo(authorization: String): AuthTokenInfoResponse
}

@Service
class AuthService : AuthServiceUseCase {

    override fun register(request: RegisterMemberRequest): RegisterMemberResponse {
        return RegisterMemberResponse("3")
    }

    override fun login(request: LoginRequest): LoginResponse {
        return LoginResponse("3")
    }

    override fun getTokenInfo(authorization: String): AuthTokenInfoResponse {
        return AuthTokenInfoResponse(authorization.toLong())
    }
}
