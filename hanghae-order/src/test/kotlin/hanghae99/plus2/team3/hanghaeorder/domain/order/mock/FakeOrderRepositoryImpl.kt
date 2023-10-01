package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.common.exception.OrderNotFoundException
import hanghae99.plus2.team3.hanghaeorder.infrastructure.order.entity.OrderEntity
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class FakeOrderRepositoryImpl(
    preSavedOrders: List<Order>
) : OrderRepository {
    private val autoGeneratedId: AtomicLong = AtomicLong(0)
    private val orders: MutableList<OrderEntity> = Collections.synchronizedList(mutableListOf<OrderEntity>())
    init {
        preSavedOrders.forEach { save(it) }
    }

    override fun save(order: Order): Order {
        if (order.id == 0L) {
            val entity = OrderEntity(
                id = autoGeneratedId.incrementAndGet(),
                orderNum = order.orderNum,
                userId = order.userId,
                deliveryInfo = order.deliveryInfo,
                orderStatus = order.orderStatus
            )
            orders.add(entity)
            return order.copy(id = entity.id)
        } else {
            orders.removeIf {
                it.id == order.id
            }
            orders.add(OrderEntity.of(order))
            return order
        }
    }

    override fun getByOrderNum(orderNum: String): Order {
        return (
            orders.find { it.orderNum == orderNum }
                ?: throw OrderNotFoundException()
            )
            .toDomain()
    }
}
