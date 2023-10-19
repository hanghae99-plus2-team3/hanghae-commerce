package hanghae99.plus2.team3.hanghaecommon.config

import hanghae99.plus2.team3.hanghaecommon.filter.RequestTraceLoggingFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * RequestTraceLogFilterConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/18/23
 */
@Configuration
class RequestTraceLogFilterConfig(
    private val log: org.slf4j.Logger
) {

    @Value("\${spring.application.name}")
    lateinit var domain: String

    @Bean
    fun loggingFilter(): FilterRegistrationBean<RequestTraceLoggingFilter> {
        val registrationBean = FilterRegistrationBean<RequestTraceLoggingFilter>()
        registrationBean.filter = RequestTraceLoggingFilter(log, domain)
        return registrationBean
    }
}
