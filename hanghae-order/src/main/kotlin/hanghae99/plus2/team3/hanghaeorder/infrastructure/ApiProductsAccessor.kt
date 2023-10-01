package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
import org.springframework.stereotype.Component

/**
 * ApiProductsAccessor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/23
 */
@Component
class ApiProductsAccessor : ProductsAccessor {
    override fun queryProduct(productIds: List<Long>): List<ProductsAccessor.ProductInfo> {
        TODO("Not yet implemented")
    }

    override fun updateProductStock(updateProductStock: List<ProductsAccessor.UpdateProductStockRequest>) {
        TODO("Not yet implemented")
    }
}
