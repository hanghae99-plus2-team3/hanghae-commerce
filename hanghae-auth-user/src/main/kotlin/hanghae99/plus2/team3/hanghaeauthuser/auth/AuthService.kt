package hanghae99.plus2.team3.hanghaeauthuser.auth

import org.springframework.stereotype.Service

interface AuthServiceUseCase {
    fun register(request: RegisterMemberRequest)
    fun login(request: LoginRequest): LoginResponse
    fun getTokenInfo(authorization: String): AuthTokenInfoResponse
}

@Service
class AuthService(
    private val authDbPort: AuthDbPort
) : AuthServiceUseCase {

    override fun register(request: RegisterMemberRequest) {
        return authDbPort.register(request)
    }

    override fun login(request: LoginRequest): LoginResponse {
        return LoginResponse("3")
    }

    override fun getTokenInfo(authorization: String): AuthTokenInfoResponse {
        return AuthTokenInfoResponse(authorization.toLong())
    }
}
