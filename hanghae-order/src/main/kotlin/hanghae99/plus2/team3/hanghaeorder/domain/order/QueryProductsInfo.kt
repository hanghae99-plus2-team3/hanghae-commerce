package hanghae99.plus2.team3.hanghaeorder.domain.order

interface QueryProductsInfo {
    fun query(productIds: List<Long>): List<ProductInfo>

    data class ProductInfo private constructor(
        val productId: Long,
        val productName: String,
        val productPrice: Long,
        val productStock: Int,
    ) {

        companion object {
            fun create(
                productId: Long,
                productName: String,
                productPrice: Long,
                productStock: Int,
            ): ProductInfo {

                return ProductInfo(
                    productId = productId,
                    productName = productName,
                    productPrice = productPrice,
                    productStock = productStock
                )
            }
        }
    }

}
