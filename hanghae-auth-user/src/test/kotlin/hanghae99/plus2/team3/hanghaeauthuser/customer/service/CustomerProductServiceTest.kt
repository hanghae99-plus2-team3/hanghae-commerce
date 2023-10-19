package hanghae99.plus2.team3.hanghaeauthuser.customer.service

import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.SearchProductRequest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CustomerProductServiceTest {

    private var sut: CustomerProductService = CustomerProductService()

    @Test
    fun `회원이 상품검색에 성공한다`() {
        val searchProduct = sut.searchProduct(
            SearchProductRequest("test")
        )

        assertThat(searchProduct).isNotNull
    }

    @Test
    fun `상품 검색에 공백 문자열은 실패한다`() {
        assertThatThrownBy {
            sut.searchProduct(
                SearchProductRequest("")
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("검색어를 입력해주세요.")
    }
}
