package hanghae99.plus2.team3.hanghaeauthuser.auth

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

interface AuthDbPort {
    fun register(member: AuthMember): AuthMember
    fun getUser(loginId: String): AuthMember
    fun saveToken(authToken: AuthToken): AuthToken
    fun getTokenInfo(authorization: String): AuthTokenInfoResponse
}

@Component
@Transactional
class AuthDbAdapter(
    private val authMemberEntityRepository: AuthMemberEntityRepository,
    private val authTokenEntityRepository: AuthTokenEntityRepository,
    private val authMemberMapper: AuthMemberMapper,
    private val authTokenMapper: AuthTokenMapper
) : AuthDbPort {

    override fun register(member: AuthMember): AuthMember {
        val newEntity = authMemberMapper.toEntity(member)
        val savedEntity = authMemberEntityRepository.save(newEntity)
        return authMemberMapper.toDomain(savedEntity)
    }

    override fun getUser(loginId: String): AuthMember {
        val entity = authMemberEntityRepository.getAuthMemberEntityByLoginId(loginId)
        return authMemberMapper.toDomain(entity)
    }

    override fun saveToken(authToken: AuthToken): AuthToken {
        val newEntity = authTokenMapper.toEntity(authToken)
        val savedEntity = authTokenEntityRepository.save(newEntity)
        return authTokenMapper.toDomain(savedEntity)
    }

    override fun getTokenInfo(authorization: String): AuthTokenInfoResponse {
        val tokenEntity = authTokenEntityRepository.getAuthTokenEntityByToken(authorization)
        return AuthTokenInfoResponse(tokenEntity.memberPk)
    }
}
