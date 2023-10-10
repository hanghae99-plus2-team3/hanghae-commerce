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
    private val authDbPort: AuthDbPort,
    private val authPasswordHandler: AuthPasswordHandler,
    private val authTokenHandler: AuthTokenHandler
) : AuthServiceUseCase {

    override fun register(request: RegisterMemberRequest): AuthMember {
        val encryptedPassword = authPasswordHandler.encrypt(request.pw)
        val newMember = AuthMember(
            pk = 0L,
            loginId = request.loginId,
            pw = encryptedPassword,
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
        require(
            authPasswordHandler.matches(
                requestPassword = request.pw,
                realPassword = member.pw
            )
        )
        return member
    }

    private fun issueNewToken(member: AuthMember): AuthToken {
        val createdTime = LocalDateTime.now()
        val newToken =
            authTokenHandler.createToken(
                memberPk = member.pk,
                loginId = member.loginId,
                createdTime = createdTime
            )

        return authDbPort.saveToken(newToken)
    }

    override fun getTokenInfo(authorization: String): AuthTokenInfoResponse {
        return authDbPort.getTokenInfo(authorization)
    }
}
