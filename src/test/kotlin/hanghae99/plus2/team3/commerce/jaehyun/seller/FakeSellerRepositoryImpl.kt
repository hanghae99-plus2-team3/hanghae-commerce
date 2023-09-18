package hanghae99.plus2.team3.commerce.jaehyun.seller

import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.Seller
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.SellerRepository

class FakeSellerRepositoryImpl(
    private val sellerMemoryRepository: SellerMemoryRepository
) : SellerRepository {
    override fun findByIdOrNull(sellerId: Long): Seller? {
        return Seller(
            id = 1L,
            name = "판매자1",
        )
    }

    override fun save(seller: Seller): Seller {
        return sellerMemoryRepository.save(seller)
    }

}
