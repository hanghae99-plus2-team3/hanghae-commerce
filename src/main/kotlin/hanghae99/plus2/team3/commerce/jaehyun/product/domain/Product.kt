package hanghae99.plus2.team3.commerce.jaehyun.product.domain

data class Product(
    val id: Long = 0,
    val name: String,
    val price: Int,
    val quantity: Int,
    val sellerId: Long,
    val shopId: Long,
    val category: Category,
) {
    init {
        require(name.isNotBlank()) { "상품 이름은 필수입니다." }
        require(price >= 0) { "잘못된 상품 가격입니다." }
        require(quantity >= 0) { "상품 수량이 0개 미만입니다." }
        require(sellerId >= 0) { "잘못된 판매자 ID 입니다." }
        require(shopId >= 0) { "잘못된 상점 ID 입니다." }
    }
}
