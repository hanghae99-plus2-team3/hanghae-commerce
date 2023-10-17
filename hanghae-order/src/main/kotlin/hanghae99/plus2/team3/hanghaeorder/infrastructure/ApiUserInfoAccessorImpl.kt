package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Component
class ApiUserInfoAccessorImpl(
    private val feignUserInfoAccessor: FeignUserInfoAccessor
) : UserInfoAccessor {
    override fun query(userId: Long) =
        UserInfoAccessor.UserInfo(
            userId = userId,
            userName = "test",
            userEmail = "testEmail"
        )
}

@FeignClient(name = "user-service", url = "http://localhost:8081/v1/user")
interface FeignUserInfoAccessor {
    @GetMapping("/{userId}")
    fun query(@PathVariable("userId") userId: Long): UserInfoAccessor.UserInfo
}
