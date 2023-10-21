package hanghae99.plus2.team3.hanghaeproduct.dto

import hanghae99.plus2.team3.hanghaeproduct.domain.ProductCategory


data class CreateProductRequest(
    var id: Long,
    var name: String,
    var price: Int,
    var color: String,
    var productCategory: ProductCategory,
    var stockQuantity: Int,
)
