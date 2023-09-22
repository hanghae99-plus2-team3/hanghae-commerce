package hanghae99.plus2.team3.hanghaeorder.domain.order

data class DeliveryInfo(
    val receiverName: String,
    val receiverPhone: String,
    val receiverZipCode: String,
    val receiverAddress1: String,
    val receiverAddress2: String,
    val message: String? = null,
) {
    init {
        require(receiverName.isNotBlank()) { "수령인 이름은 필수입니다." }
        require(receiverPhone.isNotBlank()) { "수령인 전화번호는 필수입니다." }
        require(receiverZipCode.isNotBlank()) { "수령인 우편번호는 필수입니다." }
        require(receiverZipCode.length == 5) { "수령인 우편번호는 5자리입니다." }
        require(receiverAddress1.isNotBlank()) { "수령인 주소는 필수입니다." }
        require(receiverAddress2.isNotBlank()) { "수령인 상세주소는 필수입니다." }

    }
}
