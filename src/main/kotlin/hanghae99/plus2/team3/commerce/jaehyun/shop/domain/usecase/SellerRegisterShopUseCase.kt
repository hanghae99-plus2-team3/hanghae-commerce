package hanghae99.plus2.team3.commerce.jaehyun.shop.domain.usecase

import hanghae99.plus2.team3.commerce.jaehyun.shop.domain.Shop
import hanghae99.plus2.team3.commerce.jaehyun.shop.domain.ShopRepository
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.SellerRepository
import hanghae99.plus2.team3.commerce.jaehyun.seller.exception.SellerNotFoundException

interface SellerRegisterShopUseCase {
    fun command(command: Command): Shop

    data class Command(
        val name: String,
        val sellerId: Long,
    ) {
        init {
            require(name.isNotBlank()) { "상점 이름은 필수입니다." }
            require(sellerId > 0) { "잘못된 판매자 ID 입니다." }
        }
    }
}

internal class SellerRegisterShopUseCaseImpl(
    private val shopRepository: ShopRepository,
    private val sellerRepository: SellerRepository,

    ) : SellerRegisterShopUseCase {
    override fun command(command: SellerRegisterShopUseCase.Command): Shop {

        val shop = Shop(
            name = command.name,
            seller = (sellerRepository.findByIdOrNull(command.sellerId) ?: throw SellerNotFoundException()),
        )
        return shopRepository.save(shop)
    }
}
