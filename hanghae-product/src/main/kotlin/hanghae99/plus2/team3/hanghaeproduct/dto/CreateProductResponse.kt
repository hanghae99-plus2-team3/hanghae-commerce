package hanghae99.plus2.team3.hanghaeproduct.dto

import hanghae99.plus2.team3.hanghaeproduct.domain.Product
import hanghae99.plus2.team3.hanghaeproduct.domain.ProductCategory

data class CreateProductResponse(
    val id: Long?,
    val name: String?,
    val price: Int?,
    var color: String,
    val productCategory: ProductCategory,
    val stockQuantity: Int?
) {
    companion object {
        fun of(product: Product): CreateProductResponse {
            return CreateProductResponse(
                id = product.id,
                name = product.name,
                price = product.price,
                color = product.color,
                productCategory = product.productCategory,
                stockQuantity = product.stockQuantity,
            )
        }
    }
}
