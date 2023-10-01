package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
import hanghae99.plus2.team3.hanghaeorder.common.exception.OrderedItemOutOfStockException

class FakeProductsAccessor(
    private val productInfo: MutableList<ProductsAccessor.ProductInfo>
) : ProductsAccessor {

    override fun queryProduct(productIds: List<Long>): List<ProductsAccessor.ProductInfo> {
        return productInfo.filter { productIds.contains(it.productId) }
    }

    override fun updateProductStock(updateProductStock: List<ProductsAccessor.UpdateProductStockRequest>) {
        updateProductStock.forEach {
            val product = productInfo.find { product -> product.productId == it.productId }
            if (product != null && product.productStock >= it.updateStockCount) {
                productInfo[productInfo.indexOf(product)] =
                    product.copy(productStock = product.productStock - it.updateStockCount)
            } else {
                throw OrderedItemOutOfStockException()
            }
        }
    }
}
