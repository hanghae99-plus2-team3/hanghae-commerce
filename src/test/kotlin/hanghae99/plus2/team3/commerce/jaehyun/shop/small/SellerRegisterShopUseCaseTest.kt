package hanghae99.plus2.team3.commerce.jaehyun.shop.small

import hanghae99.plus2.team3.commerce.jaehyun.common.exception.ErrorCode
import hanghae99.plus2.team3.commerce.jaehyun.shop.mock.FakeShopRepositoryImpl
import hanghae99.plus2.team3.commerce.jaehyun.shop.domain.usecase.SellerRegisterShopUseCase
import hanghae99.plus2.team3.commerce.jaehyun.shop.domain.usecase.SellerRegisterShopUseCaseImpl
import hanghae99.plus2.team3.commerce.jaehyun.shop.mock.ShopMemoryRepository
import hanghae99.plus2.team3.commerce.jaehyun.seller.mock.FakeSellerRepositoryImpl
import hanghae99.plus2.team3.commerce.jaehyun.seller.mock.SellerMemoryRepository
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.Seller
import hanghae99.plus2.team3.commerce.jaehyun.seller.exception.SellerNotFoundException
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
class SellerRegisterShopUseCaseTest {
    private lateinit var sellerRegisterShopUseCase: SellerRegisterShopUseCase

    @BeforeEach
    fun setUp() {
        val fakeSellerRepository = FakeSellerRepositoryImpl(SellerMemoryRepository())
        sellerRegisterShopUseCase = SellerRegisterShopUseCaseImpl(
            FakeShopRepositoryImpl(
                ShopMemoryRepository()
            ),
            fakeSellerRepository,
        )
        fakeSellerRepository.save(Seller(id = 1L, name = "판매자1"))
    }

    @Test
    fun `정상적으로 판매자가 상점를 등록하면 기대하는 응답을 반환한다`() {
        val command = SellerRegisterShopUseCase.Command(
            name = "상점1",
            sellerId = 1L,
        )

        val savedMarket = sellerRegisterShopUseCase.command(command)

        assertThat(savedMarket.id).isNotNull
        assertThat(savedMarket.name).isEqualTo(command.name)
        assertThat(savedMarket.seller.id).isEqualTo(command.sellerId)
    }

    @Test
    fun `등록되지 않은 sellerId로 상점 등록 요청을 하면 기대하는 응답(exception)을 반환한다`() {
        val command = SellerRegisterShopUseCase.Command(
            name = "상점1",
            sellerId = 9999L,
        )

        assertThatThrownBy { sellerRegisterShopUseCase.command(command) }
            .isExactlyInstanceOf(SellerNotFoundException::class.java)
            .hasMessage(ErrorCode.SELLER_NOT_FOUND.message)
    }
}



