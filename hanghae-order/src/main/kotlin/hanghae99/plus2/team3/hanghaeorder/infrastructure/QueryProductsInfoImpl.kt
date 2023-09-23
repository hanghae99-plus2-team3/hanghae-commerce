package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.QueryProductsInfo
import org.springframework.stereotype.Component

/**
 * QueryProductsInfo
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/23
 */
@Component
class QueryProductsInfoImpl: QueryProductsInfo {
    override fun query(productIds: List<Long>): List<QueryProductsInfo.ProductInfo> {
        TODO("Not yet implemented")
    }
}
