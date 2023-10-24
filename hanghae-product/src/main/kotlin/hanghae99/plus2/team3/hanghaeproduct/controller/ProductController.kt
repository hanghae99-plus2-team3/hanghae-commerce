package hanghae99.plus2.team3.hanghaeproduct.controller

import hanghae99.plus2.team3.hanghaeproduct.dto.CreateProductRequest
import hanghae99.plus2.team3.hanghaeproduct.dto.CreateProductResponse
import hanghae99.plus2.team3.hanghaeproduct.dto.ProductInfoResponse
import hanghae99.plus2.team3.hanghaeproduct.service.ProductService
import org.springframework.web.bind.annotation.*

@RequestMapping("/products")
@RestController
class ProductController(
    private val productService: ProductService,
) {
    @GetMapping("/{id}")
    fun getProductInfo(@PathVariable id: Long): ProductInfoResponse {
        return productService.getProductInfo(id)
    }

    @PostMapping("/product")
    fun createStore(@RequestBody request: CreateProductRequest): CreateProductResponse {
        return productService.createItem(request)
    }

//    @GetMapping("/product/{storeId}")
//    fun getProducts(@PathVariable storeId: String): List<GetProductsResponse> {
//        return productService.getProducts()
//    }
}
