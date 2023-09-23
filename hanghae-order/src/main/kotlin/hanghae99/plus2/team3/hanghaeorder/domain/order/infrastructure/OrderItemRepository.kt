package hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem

interface OrderItemRepository {
    fun save(orderItem: OrderItem): OrderItem
}
