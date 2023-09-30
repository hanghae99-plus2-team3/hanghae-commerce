package hanghae99.plus2.team3.hanghaeorder.common

import hanghae99.plus2.team3.hanghaeorder.common.filter.AuthFilter
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * FilterConfiguration
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/30
 */

@Configuration
class WebConfiguration(
    private val userInfoAccessor: UserInfoAccessor,
    private val authArgumentResolver: AuthArgumentResolver
): WebMvcConfigurer {

    @Bean
    fun authFilter(): FilterRegistrationBean<AuthFilter> {
        val registrationBean = FilterRegistrationBean<AuthFilter>()
        registrationBean.filter = AuthFilter(userInfoAccessor)
        registrationBean.addUrlPatterns("/v1/orders/*")
        return registrationBean
    }


    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authArgumentResolver)
    }
}
