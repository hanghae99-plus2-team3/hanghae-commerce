package hanghae99.plus2.team3.hanghaeorder.domain.order

data class OrderItem(
    val id: Long,
    val order: Order,
    val productId: Long,
    val quantity: Int,
    val productPrice: Long,
    val deliveryStatus: DeliveryStatus
) {

    companion object {
        fun create(
            order: Order,
            productId: Long,
            quantity: Int,
            productPrice: Long
        ): OrderItem {
            require(quantity > 0) { "상품 수량은 0보다 커야 합니다." }
            require(productPrice > 0) { "상품 가격은 0보다 커야 합니다." }

            return OrderItem(
                id = 0L,
                order = order,
                productId = productId,
                quantity = quantity,
                productPrice = productPrice,
                deliveryStatus = DeliveryStatus.BEFORE_PAYMENT
            )
        }
    }

    enum class DeliveryStatus(
        val description: String
    ) {
        BEFORE_PAYMENT("결제 대기"),
        READY("배송 준비중"),
        DELIVERY("배송 중"),
        COMPLETE("배송 완료");
    }
}
