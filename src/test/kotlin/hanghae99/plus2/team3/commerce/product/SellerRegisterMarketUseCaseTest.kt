package hanghae99.plus2.team3.commerce.product

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.atomic.AtomicLong

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
        sellerRegisterMarketUseCase = SellerRegisterMarketUseCaseImpl()
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


