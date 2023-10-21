package hanghae99.plus2.team3.hanghaeproduct.service

import hanghae99.plus2.team3.hanghaeproduct.domain.Product
import hanghae99.plus2.team3.hanghaeproduct.domain.ProductRepository
import hanghae99.plus2.team3.hanghaeproduct.dto.CreateProductRequest
import hanghae99.plus2.team3.hanghaeproduct.dto.CreateProductResponse
import hanghae99.plus2.team3.hanghaeproduct.dto.ProductInfoResponse
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {

    @Transactional
    fun getProductInfo(id: Long): ProductInfoResponse {
        val product = productRepository.findById(id)
            .orElseThrow { throw IllegalArgumentException("존재하지 않는 상품입니다") }
        return ProductInfoResponse(
            id = product.id,
            name = product.name,
            price = product.price,
            color = product.color,
            productCategory = product.productCategory,
            stockQuantity = product.stockQuantity
        )
    }

//    fun getProductsAll(): List<ProductInfoResponse> {
//        val products = productRepository.findAll()
//        return


//    fun saveProduct(request: ProductRequest) {
//        val product = Product(
//            id = 1L,
//            name = request.name,
//            price = request.price,
//            color = request.color,
//            productCategory = request.category,
//            serialNumber = request.serialNumber,
//            stockQuantity = request.stockQuantity,
//        )
//        productRepository.save(product)
//    }

    fun getProductByName(query: String): List<Product> {
        return emptyList()
    }

    fun validateStock() {

    }

    fun createItem(request: CreateProductRequest): CreateProductResponse {
        val product = Product.create(
            id = request.id,
            name = request.name,
            price = request.price,
            color = request.color,
            productCategory = request.productCategory,
            stockQuantity = request.stockQuantity
        )

        val savedItem = productRepository.save(product)


        return CreateProductResponse.of(savedItem)
    }

}
