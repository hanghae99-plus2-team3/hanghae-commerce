package hanghae99.plus2.team3.hanghaelikecart.common

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "auth", url = "http://localhost:8081/api/auth")
interface AuthFeignClient {

    @GetMapping("/info")
    fun getTokenInfo(
        @RequestHeader("authorization") authorization: String
    ): AuthTokenInfo
}

data class AuthTokenInfo(
    private val memberPk: Long
) {
    fun getMemberPk() = Member.Pk(memberPk)
}
