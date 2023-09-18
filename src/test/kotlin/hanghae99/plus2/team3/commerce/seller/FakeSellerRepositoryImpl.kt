package hanghae99.plus2.team3.commerce.seller

import hanghae99.plus2.team3.commerce.seller.domain.Seller
import hanghae99.plus2.team3.commerce.seller.domain.SellerRepository

class FakeSellerRepositoryImpl(

) : SellerRepository {
    override fun findByIdOrNull(sellerId: Long): Seller? {
        return Seller(
            id = 1L,
            name = "판매자1",
        )
    }

}
