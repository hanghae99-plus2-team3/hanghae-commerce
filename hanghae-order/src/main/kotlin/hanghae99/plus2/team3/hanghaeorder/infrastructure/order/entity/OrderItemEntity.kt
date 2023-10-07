package hanghae99.plus2.team3.hanghaeorder.infrastructure.order.entity

import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem
import jakarta.persistence.*

@Entity
class OrderItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", updatable = false, insertable = false)
    val orderEntity: OrderEntity?,
    @Column(name = "order_id", updatable = false)
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val productPrice: Long,
    @Enumerated(EnumType.STRING)
    val deliveryStatus: OrderItem.DeliveryStatus
) {

    fun toDomain() =
        OrderItem(
            id = id,
            orderId = orderId,
            productId = productId,
            quantity = quantity,
            productPrice = productPrice,
            deliveryStatus = deliveryStatus
        )

    companion object {
        fun of(
            orderItem: OrderItem
        ): OrderItemEntity {
            return OrderItemEntity(
                id = orderItem.id,
                orderId = orderItem.orderId,
                productId = orderItem.productId,
                quantity = orderItem.quantity,
                productPrice = orderItem.productPrice,
                deliveryStatus = orderItem.deliveryStatus,
                orderEntity = null
            )
        }
    }
}
