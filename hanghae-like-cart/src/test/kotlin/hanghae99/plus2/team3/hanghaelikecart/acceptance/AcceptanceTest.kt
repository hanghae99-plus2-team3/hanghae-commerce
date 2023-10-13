package hanghae99.plus2.team3.hanghaelikecart.acceptance

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceTest {

    @LocalServerPort
    private var port = 0

    @Autowired
    private var databaseCleaner: DatabaseCleaner? = null

    @BeforeEach
    fun setUp() {
        RestAssured.port = port
        databaseCleaner!!.execute()
    }
}
