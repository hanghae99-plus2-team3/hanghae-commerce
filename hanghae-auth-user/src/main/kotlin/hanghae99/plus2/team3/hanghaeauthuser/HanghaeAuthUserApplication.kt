package hanghae99.plus2.team3.hanghaeauthuser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "hanghae99.plus2.team3.hanghaeauthuser",
        "hanghae99.plus2.team3.hanghaecommon"
    ]
)
class HanghaeAuthUserApplication

fun main(args: Array<String>) {
    runApplication<HanghaeAuthUserApplication>(*args)
}
