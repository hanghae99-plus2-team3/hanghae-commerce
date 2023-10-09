package hanghae99.plus2.team3.hanghaeorder.domain.payment.service

import hanghae99.plus2.team3.hanghaeorder.common.exception.PaymentException
import hanghae99.plus2.team3.hanghaeorder.common.exception.PaymentProcessException
import hanghae99.plus2.team3.hanghaeorder.common.exception.PaymentRefundProcessException
import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.service.dto.OrderWithItemsDto
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.PaymentValidator
import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentResultCode
import hanghae99.plus2.team3.hanghaeorder.domain.payment.infrastructure.PaymentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * PaymentService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/10/01
 */

@Transactional(readOnly = true)
@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val productsAccessor: ProductsAccessor,
    private val paymentValidators: List<PaymentValidator>,
    private val paymentProcessor: PaymentProcessor
) {

    @Transactional
    fun savePaymentRequestLog(payment: Payment) {
        paymentRepository.save(payment)
        if (!payment.success) {
            throw PaymentProcessException(payment.paymentResultCode)
        }
    }

    @Transactional
    fun requestPaymentOf(
        orderWithItems: OrderWithItemsDto,
        command: OrderPaymentUseCase.Command
    ): Payment {

        validatePayment(orderWithItems, command)
        reduceProductStock(orderWithItems.orderItems)

        try {
            val payment = processPayment(
                PaymentProcessor.PaymentRequest(
                    paymentNum = orderWithItems.order.getPaymentNum(),
                    paymentVendor = command.paymentVendor,
                    paymentAmount = command.paymentAmount
                )
            )
            updateOrderStatusToPaymentCompleted(orderWithItems)
            return payment
        } catch (e: Exception) {
            rollbackProductStock(orderWithItems.orderItems)
            return Payment.createFailPayment(
                paymentNum = orderWithItems.order.getPaymentNum(),
                paymentVendor = command.paymentVendor,
                paymentAmount = command.paymentAmount,
                paymentResultCode = when (e) {
                    is PaymentException -> e.paymentResultCode
                    else -> PaymentResultCode.ERROR_ACCRUED_WHEN_PROCESSING_PAYMENT
                }
            )
        }
    }


    @Transactional
    fun requestRefundOf(cancelableOrder: OrderWithItemsDto) {
        val payment = paymentRepository.getByOrderNum(cancelableOrder.order.orderNum)

        try {
            paymentProcessor.refund(
                PaymentProcessor.RefundRequest(
                    orderNum = cancelableOrder.order.orderNum,
                    paymentVendor = payment.paymentVendor
                )
            )
            rollbackProductStock(cancelableOrder.orderItems)
        } catch (e: Exception) {
            throw PaymentRefundProcessException(PaymentResultCode.)
        }
    }

    private fun updateOrderStatusToPaymentCompleted(
        orderWithItems: OrderWithItemsDto,
    ) {
        orderRepository.save(orderWithItems.order.updateStatusToPaymentCompleted())
        orderWithItems.orderItems.forEach {
            orderItemRepository.save(it.updateStatusToPaymentCompleted())
        }
    }

    private fun validatePayment(
        orderWithItems: OrderWithItemsDto,
        command: OrderPaymentUseCase.Command
    ) {
        paymentValidators.forEach { it.validate(orderWithItems, command) }
    }

    private fun rollbackProductStock(orderItems: List<OrderItem>) {
        productsAccessor.updateProductStock(
            orderItems.map {
                ProductsAccessor.UpdateProductStockRequest(it.productId, -it.quantity)
            }
        )
    }

    private fun processPayment(
        paymentRequest: PaymentProcessor.PaymentRequest
    ) = paymentProcessor.pay(paymentRequest)

    private fun reduceProductStock(orderItems: List<OrderItem>) {
        productsAccessor.updateProductStock(
            orderItems.map {
                ProductsAccessor.UpdateProductStockRequest(it.productId, it.quantity)
            }
        )
    }


}
