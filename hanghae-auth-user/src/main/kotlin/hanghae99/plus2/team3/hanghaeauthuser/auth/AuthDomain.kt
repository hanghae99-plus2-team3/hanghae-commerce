package hanghae99.plus2.team3.hanghaeauthuser.auth

import java.time.LocalDateTime

class AuthMember(
    val pk: Long,
    val loginId: String,
    val pw: String,
    val createdTime: LocalDateTime
)

class AuthToken(
    val pk: Long,
    val token: String,
    val memberPk: Long,
    val createdTime: LocalDateTime,
    val expireTime: LocalDateTime
) {
    companion object {
        fun create(memberPk: Long, newToken: String, time: LocalDateTime): AuthToken {
            return AuthToken(
                pk = 0,
                token = newToken,
                memberPk = memberPk,
                createdTime = time,
                expireTime = time.plusDays(7)
            )
        }
    }
}
