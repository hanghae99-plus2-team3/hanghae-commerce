package hanghae99.plus2.team3.commerce.jaehyun.seller.medium

import hanghae99.plus2.team3.commerce.jaehyun.seller.interfaces.request.SellerRegistrationRequest
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

/**
 * SellerControllerTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/18
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SellerApiTest {

    @LocalServerPort
    val localServerPort: Int = 0
    val baseUrl = "/v1/seller"

    @BeforeEach
    fun setUp() {
        RestAssured.port = localServerPort
    }

    @Test
    fun `정상적으로 판매자 등록을 요청하면 기대하는 응답을 반환한다`() {
        val response = RestAssured
            .given().log().all()
            .contentType(ContentType.JSON)
            .body(SellerRegistrationRequest(name = "판매자1"))
            .`when`()
            .post(baseUrl)
            .then().log().all()
            .extract()

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())

    }
}
