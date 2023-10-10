package hanghae99.plus2.team3.hanghaeauthuser.auth

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AuthTokenHandler {

    private val tokenLifeDays = 3L

    fun createToken(
        memberPk: Long,
        loginId: String,
        createdTime: LocalDateTime
    ): AuthToken {
        val expireTime = createdTime.plusDays(tokenLifeDays)
        val token = memberPk.toString() +
            loginId +
            createdTime +
            expireTime

        return AuthToken(
            pk = 0,
            token = token,
            memberPk = memberPk,
            createdTime = createdTime,
            expireTime = expireTime
        )
    }
}

@Component
class AuthPasswordHandler {

    private val bCryptPasswordEncoder = BCryptPasswordEncoder(7)

    fun encrypt(rawPassword: String): String {
        return bCryptPasswordEncoder.encode(rawPassword)
    }

    fun matches(requestPassword: String, realPassword: String): Boolean {
        return bCryptPasswordEncoder.matches(requestPassword, realPassword)
    }
}
