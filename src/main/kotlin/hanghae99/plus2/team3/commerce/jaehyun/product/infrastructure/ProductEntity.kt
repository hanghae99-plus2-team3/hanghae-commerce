package hanghae99.plus2.team3.commerce.jaehyun.product.infrastructure

import hanghae99.plus2.team3.commerce.jaehyun.product.domain.Category

class ProductEntity(
    id: Long?,
    name: String,
    price: Int,
    quantity: Int,
    sellerId: Long,
    shopId: Long,
    category: Category,
){
    val id: Long = id ?: 0L

    var name: String = name
        private set

    var price: Int = price
        private set

    var quantity: Int = quantity
        private set

    var sellerId: Long = sellerId
        private set

    var shopId: Long = shopId
        private set

    var category: Category = category
        private set
}
