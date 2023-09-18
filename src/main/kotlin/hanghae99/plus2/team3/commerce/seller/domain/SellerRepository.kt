package hanghae99.plus2.team3.commerce.seller.domain

import hanghae99.plus2.team3.commerce.seller.domain.Seller

interface SellerRepository{
    fun findByIdOrNull(sellerId: Long): Seller?
}
