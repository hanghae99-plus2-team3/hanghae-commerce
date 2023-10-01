package hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure

interface ProductsAccessor {
    fun queryProduct(productIds: List<Long>): List<ProductInfo>
    fun updateProductStock(updateProductStock: List<UpdateProductStockRequest>)

    data class UpdateProductStockRequest(
        val productId: Long,
        val updateStockCount: Int
    )
    data class ProductInfo(
        val productId: Long,
        val productName: String,
        val productPrice: Long,
        val productStock: Int
    )
}
