package hanghae99.plus2.team3.hanghaeproduct

import hanghae99.plus2.team3.hanghaeproduct.domain.Product
import hanghae99.plus2.team3.hanghaeproduct.domain.ProductCategory

data class CreateProductResponse(
    val id: Long,
    val name: String,
    val price: Int,
    val category: ProductCategory,
) {
    companion object {
        fun of(product: Product): CreateProductResponse {
            return CreateProductResponse(
                id = product.id,
                name = product.name,
                price = product.price,
                category = product.productCategory,
            )
        }
    }
}
