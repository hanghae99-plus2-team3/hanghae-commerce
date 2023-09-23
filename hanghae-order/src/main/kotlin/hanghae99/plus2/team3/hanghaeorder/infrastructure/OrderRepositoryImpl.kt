package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import org.springframework.stereotype.Repository
import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderRepository as OrderRepository

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
}
