package hanghae99.plus2.team3.hanghaelikecart.common

import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequestAuth

@Component
class RequestAuthResolver(
    private val authFeignClient: AuthFeignClient
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(RequestAuth::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): AuthTokenInfo {
        val authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION)
            ?: throw IllegalArgumentException("로그인이 필요한 기능입니다")

        return authFeignClient.getTokenInfo(authorization = authorization)
    }
}

@Configuration
class AuthenticationConfig(
    private val requestAuthResolver: RequestAuthResolver
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(requestAuthResolver)
    }
}
