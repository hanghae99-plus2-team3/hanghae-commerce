package hanghae99.plus2.team3.commerce.market

import hanghae99.plus2.team3.commerce.market.domain.usecase.SellerRegisterMarketUseCase
import hanghae99.plus2.team3.commerce.market.domain.usecase.impl.SellerRegisterMarketUseCaseImpl
import hanghae99.plus2.team3.commerce.seller.FakeSellerRepositoryImpl
import hanghae99.plus2.team3.commerce.seller.SellerMemoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * RegisterSellerUseCaseTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/18
 */

// 판매자는 본인의 상점 정보를 등록한다.
class SellerRegisterMarketUseCaseTest {
    private lateinit var sellerRegisterMarketUseCase: SellerRegisterMarketUseCase

    @BeforeEach
    fun setUp() {
        sellerRegisterMarketUseCase = SellerRegisterMarketUseCaseImpl(
            FakeMarketRepositoryImpl(
                MarketMemoryRepository()
            ),
            FakeSellerRepositoryImpl(SellerMemoryRepository()),
        )
    }

    @Test
    fun `정상적으로 판매자가 상점를 등록하면 기대하는 응답을 반환한다`() {
        val command = SellerRegisterMarketUseCase.Command(
            name = "상점1",
            sellerId = 1L,
        )

        val savedMarket = sellerRegisterMarketUseCase.command(command)

        assertThat(savedMarket.id).isNotNull
        assertThat(savedMarket.name).isEqualTo(command.name)
        assertThat(savedMarket.sellerId).isEqualTo(command.sellerId)
    }
}



