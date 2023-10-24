package hanghae99.plus2.team3.hanghaeproduct

import hanghae99.plus2.team3.hanghaeproduct.domain.Product
import hanghae99.plus2.team3.hanghaeproduct.domain.ProductCategory
import hanghae99.plus2.team3.hanghaeproduct.domain.ProductRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductUseCase
//    private val product: Product,
//    private val productRepository: ProductRepository
{


    private fun createProduct(): Product {
        return Product(
            id = 1L,
            name = "참치",
            price = 1000,
            color = "RED",
            stockQuantity = 20,
            productCategory = ProductCategory.FOOD
        )
    }

    @Test
    fun `제품의 id를 가진 상품을 조회하면 성공을 리턴한다`() {
        val product = createProduct()
        val id = product.id
    }

    @Test
    fun `존재하지 않은 상품을 조회하면 실패를 리턴한다`() {

//        val productRepository: ProductRepository = InMemoryProductRepository()
        val product = createProduct()
//        val retrievedProductId = productRepository.findByProductId(product.id)

//        val message = assertThrows<ProductException.ProductNotFoundException> {
//            assertThat(retrievedProductId).isEqualTo(99)
//        }.message
//        assertThat(message).isEqualTo("상품이 존재하지 않습니다")

//        val exception =
//        assertThrows<ProductException.ProductNotFoundException> {
//            productRepository.findByProductId(product.id)
//        }

//        val message = "상품이 존재하지 않습니다"
//        assertThat(message).isEqualTo(exception)
    }



}
