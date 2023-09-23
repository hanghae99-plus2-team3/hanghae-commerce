package hanghae99.plus2.team3.hanghaeorder.domain.order

data class OrderItem private constructor(
    val id: Long? = null,
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val productPrice: Long,
    val deliveryStatus: DeliveryStatus,
) {

    companion object {
        fun create(
            orderId: Long,
            productId: Long,
            quantity: Int,
            productPrice: Long,
        ): OrderItem {

            require(quantity > 0) { "상품 수량은 0보다 커야 합니다." }
            require(productPrice > 0) { "상품 가격은 0보다 커야 합니다." }

            return OrderItem(
                id = null,
                orderId = orderId,
                productId = productId,
                quantity = quantity,
                productPrice = productPrice,
                deliveryStatus = DeliveryStatus.READY,
            )
        }
    }

    enum class DeliveryStatus(
        val description: String
    ) {
        READY("배송 준비중"),
        DELIVERY("배송 중"),
        COMPLETE("배송 완료");
    }
}