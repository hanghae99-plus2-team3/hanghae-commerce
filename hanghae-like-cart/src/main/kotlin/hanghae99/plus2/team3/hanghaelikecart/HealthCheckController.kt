package hanghae99.plus2.team3.hanghaelikecart

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * HealthCheckController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/20
 */

@RestController
@RequestMapping("/api/health")
class HealthCheckController {
    @RequestMapping
    fun healthCheck(): String {
        return "like-cart-ok"
    }
}
