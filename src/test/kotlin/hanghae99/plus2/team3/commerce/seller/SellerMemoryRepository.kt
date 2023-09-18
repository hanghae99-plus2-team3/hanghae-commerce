package hanghae99.plus2.team3.commerce.seller

import hanghae99.plus2.team3.commerce.seller.domain.Seller
import hanghae99.plus2.team3.commerce.seller.infrastructure.SellerEntity
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class SellerMemoryRepository{
    private val autoGeneratedId = AtomicLong(0)
    private val sellers = Collections.synchronizedList(mutableListOf<SellerEntity>())

    fun save(seller: Seller): Seller {
        val entity = SellerEntity(
            id = autoGeneratedId.incrementAndGet(),
            name = seller.name,
        )
        sellers.add(entity)
        return seller.copy(id = entity.id)
    }
}
