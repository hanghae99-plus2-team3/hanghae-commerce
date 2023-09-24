package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.infrastructure.entity.OrderEntity
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class FakeOrderRepositoryImpl : OrderRepository {
    private val autoGeneratedId = AtomicLong(0)
    private val orders = Collections.synchronizedList(mutableListOf<OrderEntity>())

    override fun save(order: Order): Order {
        val entity = OrderEntity(
            id = autoGeneratedId.incrementAndGet(),
            orderNum = order.orderNum,
            userId = order.userId,
            deliveryInfo = order.deliveryInfo,
            orderStatus = order.orderStatus,
        )
        orders.add(entity)
        return order.copy(id = entity.id)
    }

    override fun findByIdOrNull(orderId: Long): Order? {
        return orders.find { it.id == orderId }?.toDomain()
    }
}
