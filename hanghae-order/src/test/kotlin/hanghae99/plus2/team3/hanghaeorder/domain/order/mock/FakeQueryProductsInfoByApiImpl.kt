package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.QueryProductsInfoByApi
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.QueryProductsInfoByApi.*

class FakeQueryProductsInfoByApiImpl(
    private val productInfo: List<ProductInfo>
) : QueryProductsInfoByApi {

    override fun query(productIds: List<Long>): List<ProductInfo> {
        return productInfo.filter { productIds.contains(it.productId) }
    }
}
