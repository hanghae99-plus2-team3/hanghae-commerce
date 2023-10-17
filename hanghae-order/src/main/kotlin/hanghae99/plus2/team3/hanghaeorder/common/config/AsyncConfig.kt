package hanghae99.plus2.team3.hanghaeorder.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

/**
 * AsyncConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/17/23
 */

@EnableAsync
@Configuration
class AsyncConfig {

    @Bean(name = ["paymentRequestLog"])
    fun paymentRequestLogExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        val processors = Runtime.getRuntime().availableProcessors()
        executor.setThreadNamePrefix("paymentRequestLogger-")
        executor.corePoolSize = processors
        executor.maxPoolSize = processors * 2
        executor.queueCapacity = 50
        executor.keepAliveSeconds = 60
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.initialize()
        return executor
    }
}
