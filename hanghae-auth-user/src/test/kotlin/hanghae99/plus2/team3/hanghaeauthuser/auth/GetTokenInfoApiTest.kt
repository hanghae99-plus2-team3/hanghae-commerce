package hanghae99.plus2.team3.hanghaeauthuser.auth

import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class GetTokenInfoApiTest {

    @LocalServerPort
    var serverPort = 0

    @BeforeEach
    fun setUp() {
        RestAssured.port = this.serverPort
    }

    @Test
    fun `토큰 정보 조회 성공`() {
        val rawResponse = RestAssured
            .given()
            .header("authorization", "1")
            .log().all()
            .get("/api/auth/info")

        rawResponse.then().log().all()
        rawResponse.statusCode shouldBe 200
        with(rawResponse.body.`as`(AuthTokenInfoResponse::class.java)) {
            memberPk shouldBe 1L
        }
    }

    @Test
    fun `토큰 정보 조회 실패`() {
        val rawResponse = RestAssured
            .given()
            .log().all()
            .get("/api/auth/info")

        rawResponse.then().log().all()
        rawResponse.statusCode shouldBe 401
    }
}
