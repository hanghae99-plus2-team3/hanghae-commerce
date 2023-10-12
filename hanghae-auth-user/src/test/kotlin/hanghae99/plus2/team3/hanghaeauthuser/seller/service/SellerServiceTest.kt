package hanghae99.plus2.team3.hanghaeauthuser.seller.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SellerServiceTest {
    private val sut: SellerService = SellerService()

    @Test
    fun `판매자가 상점 등록에 성공한다`() {
        val marketId = sut.createMarket()

        assertThat(marketId).isNotNull
    }
}
