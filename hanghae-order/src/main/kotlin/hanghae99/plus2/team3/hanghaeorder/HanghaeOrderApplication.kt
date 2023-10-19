package hanghae99.plus2.team3.hanghaeorder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication(
    scanBasePackages =
    [
        "hanghae99.plus2.team3.hanghaeorder",
        "hanghae99.plus2.team3.hanghaecommon"
    ]
)
class HanghaeOrderApplication

fun main(args: Array<String>) {
    runApplication<HanghaeOrderApplication>(*args)
}
