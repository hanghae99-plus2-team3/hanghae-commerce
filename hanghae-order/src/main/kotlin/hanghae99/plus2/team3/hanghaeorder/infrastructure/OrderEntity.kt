package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.DeliveryInfo
import hanghae99.plus2.team3.hanghaeorder.domain.order.Order

data class OrderEntity(
    val id: Long? = null,
    val orderNum: String,
    val userId: Long,
    val deliveryInfo: DeliveryInfo,
    val orderStatus: Order.OrderStatus,
)
