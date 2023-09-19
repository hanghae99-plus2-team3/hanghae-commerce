package hanghae99.plus2.team3.commerce.jaehyun.product.domain.usecase

import hanghae99.plus2.team3.commerce.jaehyun.product.domain.Category
import hanghae99.plus2.team3.commerce.jaehyun.product.domain.Product
import hanghae99.plus2.team3.commerce.jaehyun.product.domain.ProductRepository
import hanghae99.plus2.team3.commerce.jaehyun.product.exception.InvalidShopInfoException
import hanghae99.plus2.team3.commerce.jaehyun.shop.domain.ShopRepository

interface AddProductUseCase {
    fun command(command: Command) : Product

    data class Command(
        val name: String,
        val price: Int,
        val quantity: Int,
        val sellerId: Long,
        val shopId: Long,
        val category: Category,
    ){
        init {
            require(name.isNotBlank()) { "상품 이름은 필수입니다." }
            require(price >= 0) { "잘못된 상품 가격입니다." }
            require(quantity >= 0) { "상품 수량이 0개 미만입니다." }
            require(sellerId >= 0) { "잘못된 판매자 ID 입니다." }
            require(shopId >= 0) { "잘못된 상점 ID 입니다." }
        }
    }
}

internal class AddProductUseCaseImpl(
    private val productRepository: ProductRepository,
    private val shopRepository: ShopRepository,
) : AddProductUseCase {

    override fun command(command: AddProductUseCase.Command)
        : Product {
        shopRepository.findByIdAndSellerId(command.shopId, command.sellerId)
            ?: throw InvalidShopInfoException()

        val product = Product(
            name = command.name,
            price = command.price,
            quantity = command.quantity,
            sellerId = command.sellerId,
            shopId = command.shopId,
            category = command.category,
        )
        return productRepository.save(product)
    }
}
