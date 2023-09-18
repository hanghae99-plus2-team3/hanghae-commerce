package hanghae99.plus2.team3.commerce.jaehyun.market.domain.usecase.impl

import hanghae99.plus2.team3.commerce.jaehyun.market.domain.Market
import hanghae99.plus2.team3.commerce.jaehyun.market.domain.MarketRepository
import hanghae99.plus2.team3.commerce.jaehyun.market.domain.usecase.SellerRegisterMarketUseCase
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.SellerRepository

class SellerRegisterMarketUseCaseImpl(
    private val marketRepository: MarketRepository,
    private val sellerRepository: SellerRepository,

    ) : SellerRegisterMarketUseCase {
    override fun command(command: SellerRegisterMarketUseCase.Command): Market {

        val market = Market(
            name = command.name,
            sellerId = (sellerRepository.findByIdOrNull(command.sellerId) ?: throw IllegalArgumentException()).id,
        )
        return marketRepository.save(market)
    }
}
