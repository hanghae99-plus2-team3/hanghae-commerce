package hanghae99.plus2.team3.hanghaeorder.infrastructure.entity

data class DeliveryInfoEntity(
    val receiverName: String,
    val receiverPhone: String,
    val receiverZipCode: String,
    val receiverAddress1: String,
    val receiverAddress2: String,
    val message: String? = null,
)
