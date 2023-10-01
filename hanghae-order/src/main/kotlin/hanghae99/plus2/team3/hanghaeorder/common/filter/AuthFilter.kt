package hanghae99.plus2.team3.hanghaeorder.common.filter

import hanghae99.plus2.team3.hanghaeorder.common.CurrentUser
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor
import hanghae99.plus2.team3.hanghaeorder.common.exception.AuthenticationException
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
/**
 * AuthFilter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/30
 */

class AuthFilter(
    private val userInfoAccessor: UserInfoAccessor
) : Filter {

    companion object {
        val authThreadLocal = ThreadLocal<CurrentUser>()
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val auth = (request as HttpServletRequest).getHeader(HttpHeaders.AUTHORIZATION) ?: throw AuthenticationException()
        val userInfo = userInfoAccessor.query(auth.toLong()) ?: throw AuthenticationException()

        authThreadLocal.set(CurrentUser(userInfo.userId, userInfo.userName, userInfo.userEmail))

        chain?.doFilter(request, response)

        authThreadLocal.remove()
    }
}
