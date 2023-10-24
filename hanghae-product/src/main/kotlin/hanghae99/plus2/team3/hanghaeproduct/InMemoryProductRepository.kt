package hanghae99.plus2.team3.hanghaeproduct

import hanghae99.plus2.team3.hanghaeproduct.domain.Product
import hanghae99.plus2.team3.hanghaeproduct.domain.ProductRepository

//class InMemoryProductRepository : ProductRepository {
//    private val products = mutableListOf<Product>()
//
//    override fun addProduct(product: Product) {
//        products.add(product)
//    }
//
//    override fun findByProductId(productId: Long): Product? {
//        return products.find { it.id == productId}
//            ?: throw ProductException.ProductNotFoundException()
//
//    }
//
//    override fun getAllProducts(): List<Product> {
//        return products.toList()
//    }
//
//    override fun cntSameStoreName(name: String): Int {
//        TODO("Not yet implemented")
//    }
//
//}
