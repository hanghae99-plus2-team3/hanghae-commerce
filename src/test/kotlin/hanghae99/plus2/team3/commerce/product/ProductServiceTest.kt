package hanghae99.plus2.team3.commerce.product

import org.junit.jupiter.api.Test

/**
 * ProductServiceTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/17
 */
class ProductServiceTest(
    private val productService: ProductService,
) {

    @Test
    fun `상품 등록`(){
        val request: AddProductRequest = AddProductRequest(
            name = "상품1",
            price = 1000,
            quantity = 10,
            sellerId = 1L,
            category = Category.CLOTHES,
        )

        productService.addProduct(request)
    }
}

