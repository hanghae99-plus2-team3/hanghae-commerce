package hanghae99.plus2.team3.commerce.jaehyun.seller.infrastructure

import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.Seller
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.SellerRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

/**
 * SellerRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/18
 */

@Repository
class SellerRepositoryImpl(
    private val sellerJpaRepository: SellerJpaRepository
) : SellerRepository {
    override fun findByIdOrNull(sellerId: Long): Seller? {
        return sellerJpaRepository.findByIdOrNull(sellerId)?.let {
            return Seller(
                id = it.id,
                name = it.name,
            )
        }
    }

    override fun save(seller: Seller): Seller {
        return sellerJpaRepository.save(
            SellerEntity(
                id = seller.id,
                name = seller.name,
            )
        ).let{
            return Seller(
                id = it.id,
                name = it.name,
            )
        }
    }
}
