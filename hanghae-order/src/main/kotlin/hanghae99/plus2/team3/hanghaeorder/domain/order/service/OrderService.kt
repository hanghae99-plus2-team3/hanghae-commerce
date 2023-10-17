package hanghae99.plus2.team3.hanghaeorder.domain.order.service

import hanghae99.plus2.team3.hanghaeorder.common.exception.CanNotCancelOrderException
import hanghae99.plus2.team3.hanghaeorder.common.exception.ErrorCode
import hanghae99.plus2.team3.hanghaeorder.common.exception.ProductNotFoundException
import hanghae99.plus2.team3.hanghaeorder.common.exception.ProductStockNotEnoughException
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.service.dto.OrderWithItemsDto
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.RegisterOrderUseCase
import org.springframework.stereotype.Service

/**
 * OrderService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/29
 */

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val productsAccessor: ProductsAccessor,
) {

    fun makeOrder(command: RegisterOrderUseCase.Command): String {
        validateOrderedProducts(command, getProductInfo(command))
        Thread.sleep((0..60).random().times(100).toLong())
        val savedOrder = orderRepository.save(command.toDomain())

        command.orderItemList.forEach {
            orderItemRepository.save(it.toDomain(order = savedOrder))
        }

        return savedOrder.orderNum
    }

    fun getOrderWithOrderItems(orderNum: String, userId: Long): OrderWithItemsDto {
        val order = orderRepository.getByOrderNumAndUserId(orderNum, userId)
        return OrderWithItemsDto(
            order,
            orderItemRepository.findByOrderId(order.id)
        )
    }


    private fun validateOrderedProducts(
        command: RegisterOrderUseCase.Command,
        orderedProductInfo: List<ProductsAccessor.ProductInfo>
    ) {
        command.orderItemList.forEach {
            val productInfo = orderedProductInfo.find { productInfo ->
                productInfo.productId == it.productId
            } ?: throw ProductNotFoundException()

            if (productInfo.productStock < it.quantity) {
                throw ProductStockNotEnoughException()
            }
        }
    }

    private fun getProductInfo(command: RegisterOrderUseCase.Command): List<ProductsAccessor.ProductInfo> {
        return productsAccessor.queryProduct(
            command.orderItemList.map { it.productId }
        )
    }

//    fun cancelOrder(command: CancelOrderUseCase.Command): String {
//        val order = orderRepository.getByOrderNumAndUserId(command.orderNum, command.userId)
//
//        if (!order.canCancelOrder())
//            throw CanNotCancelOrderException(ErrorCode.ORDER_PRODUCT_STARTED_DELIVERY)
//
//        val payedPayment = paymentRepository.getByOrderNum(command.orderNum)
//
//        try {
//            val payment = paymentProcessor.refund(
//                PaymentProcessor.RefundRequest(
//                    orderNum = order.orderNum,
//                    paymentVendor = payedPayment.paymentVendor,
//                )
//            )
//
//            updateOrderStatusToPaymentCompleted(order, orderItems)
//            return payment
//        } catch (e: Exception) {
//            rollbackProductStock(orderItems)
//            return Payment.createFailPayment(
//                paymentNum = order.getPaymentNum(),
//                paymentVendor = command.paymentVendor,
//                paymentAmount = command.paymentAmount,
//                paymentResultCode = when (e) {
//                    is PaymentException -> e.paymentResultCode
//                    else -> PaymentResultCode.ERROR_ACCRUED_WHEN_PROCESSING_PAYMENT
//                }
//            )
//        }
//    }

    fun getCancelableOrder(orderNum: String, userId: Long): OrderWithItemsDto {
        val order = orderRepository.getByOrderNumAndUserId(orderNum, userId)

        if (!order.canCancelOrder())
            throw CanNotCancelOrderException(ErrorCode.ORDER_PRODUCT_STARTED_DELIVERY)

        return OrderWithItemsDto(
            order,
            orderItemRepository.findByOrderId(order.id)
        )
    }
}
