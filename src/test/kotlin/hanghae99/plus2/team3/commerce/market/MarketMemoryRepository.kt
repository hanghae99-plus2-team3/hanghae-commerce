package hanghae99.plus2.team3.commerce.market

import hanghae99.plus2.team3.commerce.market.domain.Market
import hanghae99.plus2.team3.commerce.market.infrastructure.MarketEntity
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class MarketMemoryRepository {
    private val autoGeneratedId = AtomicLong(0)
    private val markets = Collections.synchronizedList(mutableListOf<MarketEntity>())

    fun save(market: Market): Market {
        val entity = MarketEntity(
            id = autoGeneratedId.incrementAndGet(),
            name = market.name,
            sellerId = market.sellerId,
        )
        markets.add(entity)
        return market.copy(id = entity.id)
    }
}
