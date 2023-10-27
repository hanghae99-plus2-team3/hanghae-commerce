package hanghae99.plus2.team3.hanghaeproduct

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages =
    [
        "hanghae99.plus2.team3.hanghaeproduct",
        "hanghae99.plus2.team3.hanghaecommon"
    ]
)
class HanghaeOrderApplication

fun main(args: Array<String>) {
    runApplication<HanghaeOrderApplication>(*args)
}
