package hanghae99.plus2.team3.hanghaeproduct.domain

import hanghae99.plus2.team3.hanghaeproduct.domain.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long>{

    fun addProduct(product: Product)
    fun getAllProducts(): List<Product>
//    fun getByProductName(productName: String): Product
    fun cntSameStoreName(name: String): Int

}
