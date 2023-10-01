package hanghae99.plus2.team3.hanghaeorder.domain.order.medium

import hanghae99.plus2.team3.hanghaeorder.domain.order.config.TestConfig
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentVendor
import hanghae99.plus2.team3.hanghaeorder.interfaces.request.OrderProductsRequest
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

/**
 * OrderApiTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/23
 */

@Import(TestConfig::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OrderApiTest {

    @LocalServerPort
    val localServerPort: Int = 0
    val baseUrl = "/v1/orders"

    @BeforeEach
    fun setUp() {
        RestAssured.port = localServerPort
    }

    @Test
    fun `정상적으로 상품을 주문하면 기대하는 응답(성공)을 반환한다`() {
        val orderProductsRequest = OrderProductsRequest(
            receiverName = "홍길동",
            receiverPhone = "010-1234-5678",
            receiverZipCode = "12345",
            receiverAddress1 = "서울시 강남구",
            receiverAddress2 = "역삼동",
            message = "부재시 경비실에 맡겨주세요",
            orderItemList = listOf(
                OrderProductsRequest.OrderItemRequest(
                    productId = 1L,
                    quantity = 1,
                    productPrice = 2000L
                ),
                OrderProductsRequest.OrderItemRequest(
                    productId = 2L,
                    quantity = 2,
                    productPrice = 3000L
                )
            )
        )
        val response = RestAssured
            .given().log().all()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, 1L)
            .body(orderProductsRequest)
            .`when`()
            .post(baseUrl)
            .then().log().all()
            .extract()

        Assertions.assertThat(response.statusCode())
            .isEqualTo(HttpStatus.CREATED.value())
    }


    @Test
    fun `정상적으로 주문한 내역의 결제 요청을 하면 기대하는 응답(성공)을 반환한다`() {
        val orderPaymentRequest = OrderPaymentRequest(
            orderNum = "orderNum-1",
            paymentVendor = PaymentVendor.KAKAO,
            paymentAmount = 10000L
        )

        val response = RestAssured
            .given().log().all()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, 1L)
            .body(orderPaymentRequest)
            .`when`()
            .post("$baseUrl/payment")
            .then().log().all()
            .extract()

        Assertions.assertThat(response.statusCode())
            .isEqualTo(HttpStatus.OK.value())
    }

}

data class OrderPaymentRequest(
    val orderNum: String,
    val paymentVendor: PaymentVendor,
    val paymentAmount: Long
)
