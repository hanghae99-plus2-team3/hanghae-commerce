package hanghae99.plus2.team3.commerce.jaehyun.market.domain.usecase

import hanghae99.plus2.team3.commerce.jaehyun.market.domain.Market
import hanghae99.plus2.team3.commerce.jaehyun.market.domain.MarketRepository
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.SellerRepository
import hanghae99.plus2.team3.commerce.jaehyun.seller.exception.SellerNotFoundException

interface SellerRegisterMarketUseCase {
    fun command(command: Command): Market

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

internal class SellerRegisterMarketUseCaseImpl(
    private val marketRepository: MarketRepository,
    private val sellerRepository: SellerRepository,

    ) : SellerRegisterMarketUseCase {
    override fun command(command: SellerRegisterMarketUseCase.Command): Market {

        val market = Market(
            name = command.name,
            sellerId = (sellerRepository.findByIdOrNull(command.sellerId) ?: throw SellerNotFoundException()).id,
        )
        return marketRepository.save(market)
    }
}
