package hanghae99.plus2.team3.hanghaeorder.infrastructure.order

import hanghae99.plus2.team3.hanghaeorder.common.exception.OrderNotFoundException
import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.infrastructure.order.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * OrderRepositoryImpl
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/23
 */

@Repository
class OrderRepositoryImpl(
    private val orderJpaRepository: OrderJpaRepository
) : OrderRepository {
    override fun save(order: Order): Order {
        return orderJpaRepository.save(OrderEntity.of(order)).toDomain()
    }

    override fun getByOrderNumAndUserId(orderNum: String, userId: Long): Order {
        return (
            orderJpaRepository.findByOrderNumAndUserId(orderNum, userId)
                ?: throw OrderNotFoundException()
            )
            .toDomain()
    }
}

interface OrderJpaRepository : JpaRepository<OrderEntity, Long> {
    fun findByOrderNumAndUserId(orderNum: String, userId: Long): OrderEntity?

}
