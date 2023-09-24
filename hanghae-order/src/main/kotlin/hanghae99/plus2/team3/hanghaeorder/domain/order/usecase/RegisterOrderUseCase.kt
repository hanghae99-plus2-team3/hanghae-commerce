package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase

import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem

interface RegisterOrderUseCase {

    fun command(command: Command): String

    data class Command(
        val userId: Long,
        val receiverName: String,
        val receiverPhone: String,
        val receiverZipCode: String,
        val receiverAddress1: String,
        val receiverAddress2: String,
        val message: String,
        val orderItemList: List<OrderItemCommand>,
    ) {

        fun toEntity(): Order {
            return Order.create(
                userId = userId,
                receiverName = receiverName,
                receiverPhone = receiverPhone,
                receiverZipCode = receiverZipCode,
                receiverAddress1 = receiverAddress1,
                receiverAddress2 = receiverAddress2,
                message = message,
            )
        }

        data class OrderItemCommand(
            val productId: Long,
            val quantity: Int,
            val productPrice: Long,
        ) {
            fun toEntity(orderId: Long): OrderItem {
                return OrderItem.create(
                    productId = productId,
                    quantity = quantity,
                    productPrice = productPrice,
                    orderId = orderId,
                )
            }
        }
    }
}

