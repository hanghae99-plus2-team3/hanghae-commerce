package hanghae99.plus2.team3.commerce.jaehyun.product.small

import hanghae99.plus2.team3.commerce.jaehyun.common.exception.ErrorCode
import hanghae99.plus2.team3.commerce.jaehyun.product.*
import hanghae99.plus2.team3.commerce.jaehyun.product.domain.Category
import hanghae99.plus2.team3.commerce.jaehyun.product.domain.usecase.AddProductUseCase
import hanghae99.plus2.team3.commerce.jaehyun.product.domain.usecase.AddProductUseCaseImpl
import hanghae99.plus2.team3.commerce.jaehyun.product.exception.InvalidShopInfoException
import hanghae99.plus2.team3.commerce.jaehyun.product.mock.FakeProductRepositoryImpl
import hanghae99.plus2.team3.commerce.jaehyun.product.mock.ProductMemoryRepository
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.Seller
import hanghae99.plus2.team3.commerce.jaehyun.seller.mock.FakeSellerRepositoryImpl
import hanghae99.plus2.team3.commerce.jaehyun.seller.mock.SellerMemoryRepository
import hanghae99.plus2.team3.commerce.jaehyun.shop.domain.Shop
import hanghae99.plus2.team3.commerce.jaehyun.shop.mock.FakeShopRepositoryImpl
import hanghae99.plus2.team3.commerce.jaehyun.shop.mock.ShopMemoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * ProductServiceTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/19
 */
class AddProductUseCaseTest {

    private lateinit var addProductUseCase: AddProductUseCase
    private lateinit var fakeSellerRepository: FakeSellerRepositoryImpl
    private lateinit var fakeShopRepository: FakeShopRepositoryImpl
    private lateinit var fakeProductRepository: FakeProductRepositoryImpl

    @BeforeEach
    fun setUp() {
        fakeSellerRepository = FakeSellerRepositoryImpl(SellerMemoryRepository())
        fakeShopRepository = FakeShopRepositoryImpl(ShopMemoryRepository())
        fakeProductRepository = FakeProductRepositoryImpl(ProductMemoryRepository())
        addProductUseCase = AddProductUseCaseImpl(fakeProductRepository, fakeShopRepository)

        val preSavedSeller = Seller(id = 1L, name = "판매자1")
        val preSavedShop = Shop(id = 1L, name = "상점1", preSavedSeller)
        fakeSellerRepository.save(preSavedSeller)
        fakeShopRepository.save(preSavedShop)
    }

    @Test
    fun `정상적으로 상품 등록을 요청하면 기대하는 응답을 반환한다`() {
        val command: AddProductUseCase.Command = AddProductUseCase.Command(
            name = "상품1",
            price = 1000,
            quantity = 10,
            sellerId = 1L,
            shopId = 1L,
            category = Category.CLOTHES,
        )

        val savedProduct = addProductUseCase.command(command)

        assertThat(savedProduct.id).isNotNull
        assertThat(savedProduct.name).isEqualTo(command.name)
        assertThat(savedProduct.price).isEqualTo(command.price)
        assertThat(savedProduct.quantity).isEqualTo(command.quantity)
        assertThat(savedProduct.sellerId).isEqualTo(command.sellerId)
        assertThat(savedProduct.shopId).isEqualTo(command.shopId)
        assertThat(savedProduct.category).isEqualTo(command.category)
    }

    @Test
    fun `잘못된 sellerId와 shopId로 상품 등록을 요청하면 기대하는 응답(exception)을 반환한다`() {
        val command: AddProductUseCase.Command = AddProductUseCase.Command(
            name = "상품1",
            price = 1000,
            quantity = 10,
            sellerId = 999L,
            shopId = 1L,
            category = Category.CLOTHES,
        )

        assertThatThrownBy {  addProductUseCase.command(command) }
            .isExactlyInstanceOf(InvalidShopInfoException::class.java)
            .hasMessage(ErrorCode.INVALID_SHOP_INFO.message)
    }
}






