package hanghae99.plus2.team3.hanghaeorder.common.filter

import hanghae99.plus2.team3.hanghaeorder.common.CurrentUser
import hanghae99.plus2.team3.hanghaeorder.common.exception.AuthenticationException
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.filter.OncePerRequestFilter

/**
 * AuthFilter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/30
 */

class AuthFilter(
    private val userInfoAccessor: UserInfoAccessor
) : OncePerRequestFilter() {

    companion object {
        val authThreadLocal = ThreadLocal<CurrentUser>()
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val auth = request.getHeader(HttpHeaders.AUTHORIZATION) ?: throw AuthenticationException()
        val userInfo = userInfoAccessor.query(auth) ?: throw AuthenticationException()

        authThreadLocal.set(CurrentUser(userInfo.pk, userInfo.name, userInfo.email))

        filterChain.doFilter(request, response)

        authThreadLocal.remove()
    }
}
