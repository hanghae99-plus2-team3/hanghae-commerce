package hanghae99.plus2.team3.hanghaeorder.infrastructure.order.entity

import hanghae99.plus2.team3.hanghaeorder.domain.order.DeliveryInfo
import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import jakarta.persistence.*

@Entity
class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val orderNum: String,
    val userId: Long,
    @Embedded
    val deliveryInfo: DeliveryInfo,
    @Enumerated(EnumType.STRING)
    val orderStatus: Order.OrderStatus
) {

    fun toDomain() =
        Order(
            id = id,
            orderNum = orderNum,
            userId = userId,
            deliveryInfo = deliveryInfo,
            orderStatus = orderStatus
        )

    companion object {
        fun of(
            order: Order
        ): OrderEntity {
            return OrderEntity(
                id = order.id,
                orderNum = order.orderNum,
                userId = order.userId,
                deliveryInfo = order.deliveryInfo,
                orderStatus = order.orderStatus
            )
        }
    }
}
