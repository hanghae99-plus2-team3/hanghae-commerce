package hanghae99.plus2.team3.commerce.jaehyun.market

import hanghae99.plus2.team3.commerce.jaehyun.market.MarketMemoryRepository
import hanghae99.plus2.team3.commerce.jaehyun.market.domain.Market
import hanghae99.plus2.team3.commerce.jaehyun.market.domain.MarketRepository

class FakeMarketRepositoryImpl(
    private val marketMemoryRepository: MarketMemoryRepository
) : MarketRepository {
    override fun save(market: Market): Market {
        return marketMemoryRepository.save(market)
    }
}
