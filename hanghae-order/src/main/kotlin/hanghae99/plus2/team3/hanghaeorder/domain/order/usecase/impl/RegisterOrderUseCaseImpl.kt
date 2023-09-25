package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.impl

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.QueryProductsInfoByApi
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.QueryUserInfoByApi
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
    private val queryProductsInfoByApi: QueryProductsInfoByApi,
    private val queryUserInfoByApi: QueryUserInfoByApi,
) : RegisterOrderUseCase {

    override fun command(command: RegisterOrderUseCase.Command): String {
        if (queryUserInfoByApi.query(command.userId) == null)
            throw OrderedUserNotFoundException()

        val orderedProductInfo =
            queryProductsInfoByApi.query(
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
        orderedProductInfo: List<QueryProductsInfoByApi.ProductInfo>
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
