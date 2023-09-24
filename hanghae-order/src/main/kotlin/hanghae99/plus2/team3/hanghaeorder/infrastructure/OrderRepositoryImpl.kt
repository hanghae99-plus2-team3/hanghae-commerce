package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.infrastructure.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
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
    private val orderJpaRepository: OrderJpaRepository,
) : OrderRepository {
    override fun save(order: Order): Order {
        return orderJpaRepository.save(OrderEntity.of(order)).toDomain()
    }

    override fun findByIdOrNull(orderId: Long): Order? {
        return orderJpaRepository.findByIdOrNull(orderId)?.toDomain()
    }
}


interface OrderJpaRepository : JpaRepository<OrderEntity, Long> {
}
