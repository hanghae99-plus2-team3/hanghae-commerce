package hanghae99.plus2.team3.hanghaeauthuser.customer.service

import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.SearchProductRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.response.SearchProductResponse
import org.springframework.stereotype.Service

@Service
class CustomerProductService {

    fun searchProduct(searchProductRequest: SearchProductRequest): SearchProductResponse {
        return SearchProductResponse("")
    }
}
