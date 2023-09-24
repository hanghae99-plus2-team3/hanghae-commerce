package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class OrderItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val productPrice: Long,
    val deliveryStatus: OrderItem.DeliveryStatus,
){

    fun toDomain() =
        OrderItem(
            id = id,
            orderId = orderId,
            productId = productId,
            quantity = quantity,
            productPrice = productPrice,
            deliveryStatus = deliveryStatus,
        )

    companion object {
        fun of(
            orderItem: OrderItem
        ):OrderItemEntity{
            return OrderItemEntity(
                id = orderItem.id,
                orderId = orderItem.orderId,
                productId = orderItem.productId,
                quantity = orderItem.quantity,
                productPrice = orderItem.productPrice,
                deliveryStatus = orderItem.deliveryStatus,
            )
        }
    }
}
