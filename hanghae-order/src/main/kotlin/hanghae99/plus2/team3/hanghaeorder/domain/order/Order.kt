package hanghae99.plus2.team3.hanghaeorder.domain.order

import java.util.*

data class Order(
    val id: Long,
    val orderNum: String,
    val userId: Long,
    val deliveryInfo: DeliveryInfo,
    val orderStatus: OrderStatus,
) {

    fun isPaymentCompleted(): Boolean {
        return orderStatus != OrderStatus.ORDERED
    }

    companion object {

        private const val PAYMENT_PREFIX = "PAYMENT"

        fun create(
            userId: Long,
            receiverName: String,
            receiverPhone: String,
            receiverZipCode: String,
            receiverAddress1: String,
            receiverAddress2: String,
            message: String
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
                    message = message
                ),
                orderStatus = OrderStatus.ORDERED
            )
        }
    }

    fun getPaymentNum(): String {
        return "$PAYMENT_PREFIX-$orderNum"
    }

    fun updateStatusToPaymentCompleted(): Order = this.copy(orderStatus = OrderStatus.PAYMENT_COMPLETED)

    fun canCancelOrder(): Boolean = orderStatus != OrderStatus.DELIVERY


    enum class OrderStatus(
        val description: String
    ) {
        ORDERED("주문 완료"),
        PAYMENT_COMPLETED("결제 완료"),
        DELIVERY("배송 중"),
    }
}
