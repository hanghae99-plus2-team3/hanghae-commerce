package hanghae99.plus2.team3.hanghaeorder.domain.order

import java.util.*

data class Order(
    val id: Long,
    val orderNum: String,
    val userId: Long,
    val deliveryInfo: DeliveryInfo,
    val orderStatus: OrderStatus,
) {


    companion object {
        fun create(
            userId: Long,
            receiverName: String,
            receiverPhone: String,
            receiverZipCode: String,
            receiverAddress1: String,
            receiverAddress2: String,
            message: String,
        ): Order {
            return Order(
                id = 0L,
                orderNum = UUID.randomUUID().toString(),
                userId = userId,
                DeliveryInfo(
                    receiverName = receiverName,
                    receiverPhone = receiverPhone,
                    receiverZipCode = receiverZipCode,
                    receiverAddress1 = receiverAddress1,
                    receiverAddress2 = receiverAddress2,
                    message = message,
                ),
                orderStatus = OrderStatus.ORDERED,
            )
        }

    }

    enum class OrderStatus(
        val description: String
    ) {
        ORDERED("주문 완료");
    }
}
