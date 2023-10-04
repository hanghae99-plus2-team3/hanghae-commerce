package hanghae99.plus2.team3.hanghaeorder.domain.order.service

import hanghae99.plus2.team3.hanghaeorder.common.exception.*
import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.CancelOrderUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.RegisterOrderUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.PaymentValidator
import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentProcessor.PaymentRequest
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentResultCode
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
    private val paymentValidators: List<PaymentValidator>,
    private val paymentProcessor: PaymentProcessor
) {

    fun makeOrder(command: RegisterOrderUseCase.Command): String {
        validateOrderedProducts(command, getProductInfo(command))

        val savedOrder = orderRepository.save(command.toDomain())

        command.orderItemList.forEach {
            orderItemRepository.save(it.toDomain(order = savedOrder))
        }

        return savedOrder.orderNum
    }

    fun makePaymentForOder(command: OrderPaymentUseCase.Command): Payment {
        val order = orderRepository.getByOrderNum(command.orderNum)
        val orderItems = orderItemRepository.findByOrderId(order.id)

        validatePayment(order, orderItems, command)
        reduceProductStock(orderItems)

        try {
            val payment = processPayment(
                PaymentRequest(
                    paymentNum = order.getPaymentNum(),
                    paymentVendor = command.paymentVendor,
                    paymentAmount = command.paymentAmount
                )
            )
            updateStatusToPaymentCompleted(order, orderItems)
            return payment
        } catch (e: Exception) {
            rollbackProductStock(orderItems)
            return Payment.createFailPayment(
                paymentNum = order.getPaymentNum(),
                paymentVendor = command.paymentVendor,
                paymentAmount = command.paymentAmount,
                paymentResultCode = when (e) {
                    is PaymentException -> e.paymentResultCode
                    else -> PaymentResultCode.ERROR_ACCRUED_WHEN_PROCESSING_PAYMENT
                }
            )
        }
    }

    private fun updateStatusToPaymentCompleted(
        order: Order,
        orderItems: List<OrderItem>
    ) {
        orderRepository.save(order.updateStatusToPaymentCompleted())
        orderItems.forEach {
            orderItemRepository.save(it.updateStatusToPaymentCompleted())
        }
    }

    private fun rollbackProductStock(orderItems: List<OrderItem>) {
        productsAccessor.updateProductStock(
            orderItems.map {
                ProductsAccessor.UpdateProductStockRequest(it.productId, -it.quantity)
            }
        )
    }

    private fun processPayment(
        paymentRequest: PaymentRequest
    ) = paymentProcessor.pay(paymentRequest)

    private fun reduceProductStock(orderItems: List<OrderItem>) {
        productsAccessor.updateProductStock(
            orderItems.map {
                ProductsAccessor.UpdateProductStockRequest(it.productId, it.quantity)
            }
        )
    }

    private fun validatePayment(
        order: Order,
        orderItems: List<OrderItem>,
        command: OrderPaymentUseCase.Command
    ) {
        paymentValidators.forEach { it.validate(order, orderItems, command) }
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

    fun cancelOrder(command: CancelOrderUseCase.Command): String {
        val order = orderRepository.getByOrderNum(command.orderNum)
        if (order.userId != command.userId)
            throw OrderNotFoundException()

        if (!order.canCancelOrder())
            throw CanNotCancelOrderException(ErrorCode.ORDER_PRODUCT_STARTED_DELIVERY)

        return order.orderNum
    }
}
