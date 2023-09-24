package hanghae99.plus2.team3.hanghaeorder.interfaces.request

import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.RegisterOrderUseCase

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
        val productPrice: Long,
    )
}
 fun OrderProductsRequest.toCommand(): RegisterOrderUseCase.Command {
    return RegisterOrderUseCase.Command(
        userId = userId,
        receiverName = receiverName,
        receiverPhone = receiverPhone,
        receiverZipCode = receiverZipCode,
        receiverAddress1 = receiverAddress1,
        receiverAddress2 = receiverAddress2,
        message = message,
        orderItemList = orderItemList.map {
            RegisterOrderUseCase.Command.OrderItemCommand(
                productId = it.productId,
                quantity = it.quantity,
                productPrice = it.productPrice,
            )
        },
    )
}
