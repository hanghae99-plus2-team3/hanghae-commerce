package hanghae99.plus2.team3.commerce.jaehyun.market

import hanghae99.plus2.team3.commerce.jaehyun.market.domain.usecase.SellerRegisterMarketUseCase
import hanghae99.plus2.team3.commerce.jaehyun.market.domain.usecase.impl.SellerRegisterMarketUseCaseImpl
import hanghae99.plus2.team3.commerce.jaehyun.seller.FakeSellerRepositoryImpl
import hanghae99.plus2.team3.commerce.jaehyun.seller.SellerMemoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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

    @Test
    fun `등록되지 않은 sellerId로 상점 등록 요청을 하면 기대하는 응답(exception)을 반환한다`() {
        val command = SellerRegisterMarketUseCase.Command(
            name = "상점1",
            sellerId = 9999L,
        )

        assertThatThrownBy { sellerRegisterMarketUseCase.command(command) }
            .isExactlyInstanceOf(SellerNotFoundException::class.java)
            .hasMessage(ErrorCode.SELLER_NOT_FOUND.message)
    }
}

enum class ErrorCode (
    val message: String,
){
    SELLER_NOT_FOUND(message = "등록되지 않은 판매자 ID 입니다."),
    ;
}

open class SellerException(
    errorCode: ErrorCode
) : RuntimeException(errorCode.message)

class SellerNotFoundException : SellerException(ErrorCode.SELLER_NOT_FOUND)


