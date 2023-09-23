package hanghae99.plus2.team3.hanghaeorder.domain.order

interface QueryProductsInfo {
    fun query(productIds: List<Long>): List<ProductInfo>

    data class ProductInfo (
        val productId: Long,
        val productName: String,
        val productPrice: Long,
        val productStock: Int,
    )

}
