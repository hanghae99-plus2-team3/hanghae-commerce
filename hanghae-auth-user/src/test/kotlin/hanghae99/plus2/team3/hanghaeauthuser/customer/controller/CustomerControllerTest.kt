package hanghae99.plus2.team3.hanghaeauthuser.customer.controller

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import javax.sql.DataSource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class CustomerControllerTest {

    @Autowired
    lateinit var dataSource: DataSource

    @LocalServerPort
    val localServerPort: Int = 0
    val baseUrl = "/v1/customer"
}
