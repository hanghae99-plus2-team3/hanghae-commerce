package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.order.QueryProductsInfo
import hanghae99.plus2.team3.hanghaeorder.domain.order.QueryProductsInfo.*

class FakeQueryProductsInfoImpl(
    private val productInfo: List<ProductInfo>
) : QueryProductsInfo {

    override fun query(productIds: List<Long>): List<ProductInfo> {
        return productInfo.filter { productIds.contains(it.productId) }
    }
}
