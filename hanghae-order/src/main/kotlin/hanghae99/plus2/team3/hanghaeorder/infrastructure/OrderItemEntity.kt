package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem

data class OrderItemEntity(
    val id: Long? = null,
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val productPrice: Long,
    val deliveryStatus: OrderItem.DeliveryStatus,
)
