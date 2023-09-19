package hanghae99.plus2.team3.commerce.jaehyun.product.domain

import hanghae99.plus2.team3.commerce.jaehyun.product.domain.Product

interface ProductRepository {
    fun save(product: Product): Product
}
