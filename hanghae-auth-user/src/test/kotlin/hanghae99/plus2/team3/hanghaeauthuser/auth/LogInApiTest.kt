package hanghae99.plus2.team3.hanghaeauthuser.auth

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class LogInApiTest {

    @LocalServerPort
    var serverPort = 0

    @BeforeEach
    fun setUp() {
        RestAssured.port = this.serverPort
    }

    @Test
    fun `로그인`() {
        val request = LoginRequest(
            loginId = "jay",
            pw = "1q2w3e4r"
        )

        val rawResponse = RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(request)
            .log().all()
            .post("/api/auth/login")

        rawResponse.then().log().all()
        rawResponse.statusCode shouldBe 200
        shouldNotThrowAny {
            rawResponse.body.`as`(LoginResponse::class.java)
        }
    }
}
