package hanghae99.plus2.team3.hanghaeauthuser.auth

import org.springframework.stereotype.Service
import java.time.LocalDateTime

interface AuthServiceUseCase {
    fun register(request: RegisterMemberRequest): AuthMember
    fun login(request: LoginRequest): AuthToken
    fun getTokenInfo(authorization: String): AuthTokenInfoResponse
}

@Service
class AuthService(
    private val authDbPort: AuthDbPort
) : AuthServiceUseCase {

    override fun register(request: RegisterMemberRequest): AuthMember {
        val newMember = AuthMember(
            pk = 0L,
            loginId = request.loginId,
            pw = request.pw,
            createdTime = LocalDateTime.now()
        )
        return authDbPort.register(newMember)
    }

    override fun login(request: LoginRequest): AuthToken {
        val authedMember = getAuthedMember(request)
        return issueNewToken(authedMember)
    }

    private fun getAuthedMember(request: LoginRequest): AuthMember {
        val member = authDbPort.getUser(request.id)
        require(member.pw == request.pw)
        return member
    }

    private fun issueNewToken(member: AuthMember): AuthToken {
        val newToken = AuthToken.create(member.pk, "HangHae", LocalDateTime.now())
        return authDbPort.saveToken(newToken)
    }

    override fun getTokenInfo(authorization: String): AuthTokenInfoResponse {
        return authDbPort.getTokenInfo(authorization)
    }
}
