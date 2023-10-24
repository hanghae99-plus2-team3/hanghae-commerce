package hanghae99.plus2.team3.hanghaeproduct.domain

import java.util.UUID

class Product(
    val id: Long,
    val name: String,
    val price: Int,
    val color: String,
    val productCategory: ProductCategory,
    val stockQuantity: Int,
) {
    companion object {
        fun create(
            id: Long,
            name: String,
            price: Int,
            color: String,
            productCategory: ProductCategory,
            stockQuantity: Int
        ): Product {
            return Product(
                id = id,
                name = name,
                price = price,
                color = color,
                productCategory = productCategory,
                stockQuantity = stockQuantity,
            )
        }
    }

}
