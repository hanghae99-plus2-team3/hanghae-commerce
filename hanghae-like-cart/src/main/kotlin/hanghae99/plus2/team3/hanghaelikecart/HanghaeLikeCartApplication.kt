package hanghae99.plus2.team3.hanghaelikecart

import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration

@ImportAutoConfiguration(FeignAutoConfiguration::class)
@EnableFeignClients
@SpringBootApplication
class HanghaeLikeCartApplication

fun main(args: Array<String>) {
    runApplication<HanghaeLikeCartApplication>(*args)
}
