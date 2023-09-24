package hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.Order

interface OrderRepository {
    fun save(order: Order): Order
    fun findByIdOrNull(orderId: Long): Order?
}
