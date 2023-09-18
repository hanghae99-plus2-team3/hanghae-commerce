package hanghae99.plus2.team3.commerce.market.domain

data class Market(
    val id: Long = 0,
    val name: String,
    val sellerId: Long,
) {
    init {
        require(name.isNotBlank()) { "상점 이름은 필수입니다." }
        require(sellerId > 0) { "잘못된 판매자 ID 입니다." }
    }
}
