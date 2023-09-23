package hanghae99.plus2.team3.hanghaeorder.interfaces.request

data class OrderProductsRequest(
    val userId: Long,
    val receiverName: String,
    val receiverPhone: String,
    val receiverZipCode: String,
    val receiverAddress1: String,
    val receiverAddress2: String,
    val message: String,
    val orderItemList: List<OrderItemRequest>,
) {

    data class OrderItemRequest(
        val productId: Long,
        val quantity: Int,
    )
}
