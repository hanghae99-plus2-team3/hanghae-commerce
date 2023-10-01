package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.infrastructure.order.entity.OrderEntity
import hanghae99.plus2.team3.hanghaeorder.infrastructure.order.entity.OrderItemEntity
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class FakeOrderItemRepositoryImpl(
    preSavedOrderItems: List<OrderItem>
) : OrderItemRepository {
    private val autoGeneratedId: AtomicLong = AtomicLong(0)
    private val orderItems: MutableList<OrderItemEntity> = Collections.synchronizedList(mutableListOf<OrderItemEntity>())

    init {
        preSavedOrderItems.forEach { save(it) }
    }

    override fun save(orderItem: OrderItem): OrderItem {
        val entity = OrderItemEntity(
            id = autoGeneratedId.incrementAndGet(),
            orderEntity = OrderEntity.of(orderItem.order),
            productId = orderItem.productId,
            quantity = orderItem.quantity,
            productPrice = orderItem.productPrice,
            deliveryStatus = orderItem.deliveryStatus
        )
        orderItems.add(entity)
        return orderItem.copy(id = entity.id)
    }

    override fun findByOrderId(orderId: Long): List<OrderItem> {
        return orderItems.filter { it.orderEntity.id == orderId }
            .map { it.toDomain() }
    }
}
