package hanghae99.plus2.team3.commerce.jaehyun.shop.domain

import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.Seller

data class Shop(
    val id: Long? = null,
    val name: String,
    val seller: Seller,
) {
    init {
        require(name.isNotBlank()) { "상점 이름은 필수입니다." }
    }
}
