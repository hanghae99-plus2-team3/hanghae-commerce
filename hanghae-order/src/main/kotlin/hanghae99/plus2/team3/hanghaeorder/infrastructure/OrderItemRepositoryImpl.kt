package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.infrastructure.entity.OrderItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * OrderItemRepositoryImpl
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/23
 */
@Repository
class OrderItemRepositoryImpl(
    private val orderItemJpaRepository: OrderItemJpaRepository,
) : OrderItemRepository {
    override fun save(orderItem: OrderItem): OrderItem {
        return orderItemJpaRepository.save(OrderItemEntity.of(orderItem)).toDomain()
    }

    override fun findByOrderId(orderId: Long): List<OrderItem> {
        return orderItemJpaRepository.findByOrderEntityId(orderId).map { it.toDomain() }
    }
}

interface OrderItemJpaRepository : JpaRepository<OrderItemEntity, Long> {
    fun findByOrderEntityId(orderId: Long): List<OrderItemEntity>
}
