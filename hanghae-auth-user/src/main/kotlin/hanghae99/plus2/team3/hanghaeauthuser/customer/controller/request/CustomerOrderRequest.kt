package hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request

data class CustomerOrderRequest(
    val receiverName: String,
    val receiverPhone: String,
    val receiverZipCode: String,
    val receiverAddress1: String,
    val receiverAddress2: String,
    val message: String,
    val productId: Long
)
