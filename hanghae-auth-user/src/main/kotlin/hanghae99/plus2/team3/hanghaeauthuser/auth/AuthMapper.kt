package hanghae99.plus2.team3.hanghaeauthuser.auth

import org.springframework.stereotype.Component

@Component
class AuthMemberMapper {

    fun toDomain(authMemberEntity: AuthMemberEntity): AuthMember {
        return AuthMember(
            pk = authMemberEntity.pk,
            loginId = authMemberEntity.loginId,
            pw = authMemberEntity.pw,
            createdTime = authMemberEntity.createdTime
        )
    }

    fun toEntity(authMember: AuthMember): AuthMemberEntity {
        return AuthMemberEntity(
            pk = authMember.pk,
            loginId = authMember.loginId,
            pw = authMember.pw,
            createdTime = authMember.createdTime
        )
    }
}

@Component
class AuthTokenMapper {

    fun toDomain(authTokenEntity: AuthTokenEntity): AuthToken {
        return AuthToken(
            pk = authTokenEntity.pk,
            token = authTokenEntity.token,
            memberPk = authTokenEntity.memberPk,
            createdTime = authTokenEntity.createdTime,
            expireTime = authTokenEntity.expireTime
        )
    }

    fun toEntity(authToken: AuthToken): AuthTokenEntity {
        return AuthTokenEntity(
            pk = authToken.pk,
            token = authToken.token,
            memberPk = authToken.memberPk,
            createdTime = authToken.createdTime,
            expireTime = authToken.expireTime
        )
    }
}
