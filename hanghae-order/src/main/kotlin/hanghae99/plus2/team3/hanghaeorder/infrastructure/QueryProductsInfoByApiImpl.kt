package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.QueryProductsInfoByApi
import org.springframework.stereotype.Component

/**
 * QueryProductsInfo
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/23
 */
@Component
class QueryProductsInfoByApiImpl: QueryProductsInfoByApi {
    override fun query(productIds: List<Long>): List<QueryProductsInfoByApi.ProductInfo> {
        TODO("Not yet implemented")
    }
}
