package hanghae99.plus2.team3.commerce.jaehyun.seller.domain


interface SellerRepository{
    fun findByIdOrNull(sellerId: Long): Seller?
    fun save(seller: Seller): Seller
    fun findByName(name: String): Seller?
}
