package hanghae99.plus2.team3.hanghaeauthuser.auth

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

interface AuthMemberEntityJpaRepository : JpaRepository<AuthMemberEntity, Long> {
    fun findByLoginId(loginId: String): AuthMemberEntity?
}

@Component
class AuthMemberEntityRepository(
    private val authMemberEntityJpaRepository: AuthMemberEntityJpaRepository
) {
    fun save(authMemberEntity: AuthMemberEntity): AuthMemberEntity {
        return authMemberEntityJpaRepository.save(authMemberEntity)
    }

    fun getAuthMemberEntityByLoginId(loginId: String): AuthMemberEntity {
        return authMemberEntityJpaRepository.findByLoginId(loginId)
            ?: throw IllegalArgumentException("해당 아이디의 회원이 없습니다.")
    }
}

interface AuthTokenEntityJpaRepository : JpaRepository<AuthTokenEntity, Long> {
    fun getByToken(token: String): AuthTokenEntity
}

@Component
class AuthTokenEntityRepository(
    private val authTokenEntityJpaRepository: AuthTokenEntityJpaRepository
) {
    fun save(authTokenEntity: AuthTokenEntity): AuthTokenEntity {
        return authTokenEntityJpaRepository.save(authTokenEntity)
    }

    fun getAuthTokenEntityByToken(token: String): AuthTokenEntity {
        return authTokenEntityJpaRepository.getByToken(token)
    }
}
