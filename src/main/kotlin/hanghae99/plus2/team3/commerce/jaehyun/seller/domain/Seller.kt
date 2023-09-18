package hanghae99.plus2.team3.commerce.jaehyun.seller.domain

data class Seller(
    val id: Long=0,
    val name: String,
) {
    init {
        require(name.isNotBlank()) { "판매자 이름은 필수입니다." }
    }
}
