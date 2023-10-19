package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaecommon.filter.RequestTraceLoggingFilter.Companion.REQUEST_TRACE_ID
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor
import org.slf4j.MDC
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

@Component
class ApiUserInfoAccessorImpl(
    private val feignUserInfoAccessor: FeignUserInfoAccessor
) : UserInfoAccessor {
    override fun query(token: String): UserInfoAccessor.UserInfo {
        val traceId = MDC.get(REQUEST_TRACE_ID)
        val userId = feignUserInfoAccessor.query(token, traceId)
        val userInfo = feignUserInfoAccessor.query(userId.memberPk, traceId)
        return userInfo
    }
}

@FeignClient(name = "user-service")
interface FeignUserInfoAccessor {
    @GetMapping("/auth/info")
    fun query(
        @RequestHeader("authorization") userId: String,
        @RequestHeader(REQUEST_TRACE_ID) traceId: String,
        @RequestHeader(HttpHeaders.CONTENT_TYPE) contentType: String = "application/json"
    ): UserTokenResponse

    @GetMapping("/customer/{userId}")
    fun query(
        @PathVariable("userId") userId: Long,
        @RequestHeader(REQUEST_TRACE_ID) traceId: String = MDC.get("REQUEST_TRACE_ID"),
        @RequestHeader(HttpHeaders.CONTENT_TYPE) contentType: String = "application/json"
    ): UserInfoAccessor.UserInfo

    data class UserTokenResponse(
        val memberPk: Long
    )
}
