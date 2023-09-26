package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.impl

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.*
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.RegisterOrderUseCase
import hanghae99.plus2.team3.hanghaeorder.exception.OrderedUserNotFoundException
import hanghae99.plus2.team3.hanghaeorder.exception.ProductNotFoundException
import hanghae99.plus2.team3.hanghaeorder.exception.ProductStockNotEnoughException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RegisterOrderUseCaseImpl(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val productsAccessor: ProductsAccessor,
    private val queryUserInfoByApi: QueryUserInfoByApi,
) : RegisterOrderUseCase {

    override fun command(command: RegisterOrderUseCase.Command): String {
        if (queryUserInfoByApi.query(command.userId) == null)
            throw OrderedUserNotFoundException()

        val orderedProductInfo =
            productsAccessor.queryProduct(
                command.orderItemList.map { it.productId }
            )

        validateOrderedProducts(command, orderedProductInfo)

        val savedOrder = orderRepository.save(command.toDomain())

        command.orderItemList.forEach {
            orderItemRepository.save(it.toDomain(order = savedOrder))
        }

        return savedOrder.orderNum
    }

    private fun validateOrderedProducts(
        command: RegisterOrderUseCase.Command,
        orderedProductInfo: List<ProductsAccessor.ProductInfo>
    ) {
        command.orderItemList.forEach {
            val productInfo = orderedProductInfo.find { productInfo ->
                productInfo.productId == it.productId
            } ?: throw ProductNotFoundException()

            if (productInfo.productStock < it.quantity) {
                throw ProductStockNotEnoughException()
            }
        }
    }
}
