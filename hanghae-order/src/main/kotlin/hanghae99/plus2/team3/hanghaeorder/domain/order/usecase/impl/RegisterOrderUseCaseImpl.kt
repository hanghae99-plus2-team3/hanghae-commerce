package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.impl

import hanghae99.plus2.team3.hanghaeorder.domain.order.*
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.RegisterOrderUseCase
import hanghae99.plus2.team3.hanghaeorder.exception.OrderedUserNotFoundException
import hanghae99.plus2.team3.hanghaeorder.exception.ProductNotFoundException
import hanghae99.plus2.team3.hanghaeorder.exception.ProductStockNotEnoughException

class RegisterOrderUseCaseImpl(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val queryProductsInfo: QueryProductsInfo,
    private val queryUserInfo: QueryUserInfo,
) : RegisterOrderUseCase {

    override fun command(command: RegisterOrderUseCase.Command): String {
        if (queryUserInfo.query(command.userId) == null)
            throw OrderedUserNotFoundException()

        val orderedProductInfo =
            queryProductsInfo.query(
                command.orderItemList.map { it.productId }
            )

        validateOrderedProducts(command, orderedProductInfo)

        val savedOrder = orderRepository.save(command.toEntity())

        command.orderItemList.forEach {
            orderItemRepository.save(it.toEntity(orderId = savedOrder.id!!))
        }

        return savedOrder.orderNum
    }

    private fun validateOrderedProducts(
        command: RegisterOrderUseCase.Command,
        orderedProductInfo: List<QueryProductsInfo.ProductInfo>
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
