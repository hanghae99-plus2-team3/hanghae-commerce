package hanghae99.plus2.team3.hanghaeauthuser.auth

import org.springframework.stereotype.Component
import java.time.LocalDateTime

interface AuthDbPort {
    fun register(registerMemberRequest: RegisterMemberRequest)
    fun login(loginRequest: LoginRequest): LoginResponse
    fun getTokenInfo(authorization: String): AuthTokenInfoResponse
}

@Component
class AuthDbAdapter(
    private val authMemberEntityRepository: AuthMemberEntityRepository,
    private val authTokenEntityRepository: AuthTokenEntityRepository
) : AuthDbPort {

    override fun register(registerMemberRequest: RegisterMemberRequest) {
        val newEntity = AuthMemberEntity(
            pk = 0,
            loginId = registerMemberRequest.loginId,
            pw = registerMemberRequest.pw,
            createdTime = LocalDateTime.now()
        )
        authMemberEntityRepository.save(newEntity)
    }

    // 이렇게 되면 로직을 여기서 가지게 되는데 서비스로 내려야하나
    override fun login(loginRequest: LoginRequest): LoginResponse {
        val authMemberEntity = authMemberEntityRepository.getAuthMemberEntityByLoginId(loginRequest.loginId)
        validatePasswordMatches(authMemberEntity, loginRequest.pw)
        val tokenEntity = issueNewToken(authMemberEntity)
        return LoginResponse(
            token = tokenEntity.token
        )
    }

    private fun validatePasswordMatches(authMemberEntity: AuthMemberEntity, requestPw: String) {
        require(authMemberEntity.pw == requestPw)
    }

    private fun issueNewToken(authMemberEntity: AuthMemberEntity): AuthTokenEntity {
        val newTokenEntity = AuthTokenEntity(
            pk = 0,
            token = authMemberEntity.pk.toString(),
            memberPk = authMemberEntity.pk,
            createdTime = LocalDateTime.now()
        )
        return authTokenEntityRepository.save(newTokenEntity)
    }

    override fun getTokenInfo(authorization: String): AuthTokenInfoResponse {
        val tokenEntity = authTokenEntityRepository.getAuthTokenEntityByToken(authorization)
        return AuthTokenInfoResponse(
            memberPk = tokenEntity.memberPk
        )
    }
}
