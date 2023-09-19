package hanghae99.plus2.team3.commerce.jaehyun.product.small

import hanghae99.plus2.team3.commerce.jaehyun.product.domain.Category
import hanghae99.plus2.team3.commerce.jaehyun.product.domain.Product
import hanghae99.plus2.team3.commerce.jaehyun.product.mock.FakeProductRepositoryImpl
import hanghae99.plus2.team3.commerce.jaehyun.product.mock.ProductMemoryRepository
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.Seller
import hanghae99.plus2.team3.commerce.jaehyun.seller.mock.FakeSellerRepositoryImpl
import hanghae99.plus2.team3.commerce.jaehyun.seller.mock.SellerMemoryRepository
import hanghae99.plus2.team3.commerce.jaehyun.shop.domain.Shop
import hanghae99.plus2.team3.commerce.jaehyun.shop.mock.FakeShopRepositoryImpl
import hanghae99.plus2.team3.commerce.jaehyun.shop.mock.ShopMemoryRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * UpdateProductInfoUseCaseTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/19
 */
class UpdateProductInfoUseCaseTest {


    private lateinit var updateProductUseCase: UpdateProductUseCase
    private lateinit var sellerRepository: FakeSellerRepositoryImpl
    private lateinit var shopRepository: FakeShopRepositoryImpl
    private lateinit var productRepository: FakeProductRepositoryImpl

    @BeforeEach
    fun setUp() {
        sellerRepository = FakeSellerRepositoryImpl(SellerMemoryRepository())
        shopRepository = FakeShopRepositoryImpl(ShopMemoryRepository())
        productRepository = FakeProductRepositoryImpl(ProductMemoryRepository())
        updateProductUseCase = UpdateProductUseCaseImpl(productRepository)

        val preSavedSeller = Seller(id = 1L, name = "판매자1")
        val preSavedShop = Shop(id = 1L, name = "상점1", preSavedSeller)
        val preSavedProduct = Product(
            id = 1L,
            name = "상품1",
            price = 1000,
            quantity = 10,
            sellerId = preSavedSeller.id!!,
            shopId = preSavedShop.id!!,
            category = Category.CLOTHES
        )
        sellerRepository.save(preSavedSeller)
        shopRepository.save(preSavedShop)
    }

    @Test
    fun `정상적으로 상품 수정을 요청하면 기대하는 응답을 반환한다`() {
        val command: UpdateProductUseCase.Command = UpdateProductUseCase.Command(
            id = 1L,
            name = "상품222",
            price = 2000,
            quantity = 30,
            category = Category.CLOTHES,
        )

        updateProductUseCase.command(command)

        val updatedProduct = productRepository.findByIdOrNull(command.id) ?: throw ProductNotFoundException()

        Assertions.assertThat(updatedProduct.name).isEqualTo(command.name)
        Assertions.assertThat(updatedProduct.price).isEqualTo(command.price)
        Assertions.assertThat(updatedProduct.quantity).isEqualTo(command.quantity)
        Assertions.assertThat(updatedProduct.category).isEqualTo(command.category)
    }

}

interface UpdateProductUseCase {

    fun command(command: Command)

    data class Command(
        val id: Long,
        val name: String,
        val price: Int,
        val quantity: Int,
        val category: Category,
    )
}
