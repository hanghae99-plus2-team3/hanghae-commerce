package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem
import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.infrastructure.OrderItemEntity
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class FakeOrderItemRepositoryImpl : OrderItemRepository {
    private val autoGeneratedId = AtomicLong(0)
    private val orderItems = Collections.synchronizedList(mutableListOf<OrderItemEntity>())

    override fun save(orderItem: OrderItem): OrderItem {
        val entity = OrderItemEntity(
            id = autoGeneratedId.incrementAndGet(),
            orderId = orderItem.orderId,
            productId = orderItem.productId,
            quantity = orderItem.quantity,
            productPrice = orderItem.productPrice,
            deliveryStatus = orderItem.deliveryStatus,
        )
        orderItems.add(entity)
        return orderItem.copy(id = entity.id)
    }
}
