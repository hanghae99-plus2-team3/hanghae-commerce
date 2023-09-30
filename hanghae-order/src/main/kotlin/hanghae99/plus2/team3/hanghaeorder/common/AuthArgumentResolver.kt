package hanghae99.plus2.team3.hanghaeorder.common

import hanghae99.plus2.team3.hanghaeorder.common.filter.AuthFilter
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * AuthArgumentResolver
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/30
 */

@Component
class AuthArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean
    = parameter.parameterType == CurrentUser::class.java

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? = AuthFilter.authThreadLocal.get()

}
