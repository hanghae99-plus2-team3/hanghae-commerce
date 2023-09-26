package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.impl

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.PaymentValidator
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.exception.OrderNotFoundException
import hanghae99.plus2.team3.hanghaeorder.exception.OrderedUserNotFoundException

class OrderPaymentUseCaseImpl(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val userInfoAccessor: UserInfoAccessor,
    private val productsAccessor: ProductsAccessor,
    private val paymentValidator: List<PaymentValidator>,
    private val paymentProcessor : PaymentProcessor,
) : OrderPaymentUseCase {

    companion object {
        private const val PAYMENT_PREFIX = "PAYMENT"
    }

    override fun command(command: OrderPaymentUseCase.Command): String {
        if (userInfoAccessor.query(command.userId) == null)
            throw OrderedUserNotFoundException()

        val order = (orderRepository.findByOrderNum(command.orderNum) ?: throw OrderNotFoundException())
        val orderItems = orderItemRepository.findByOrderId(order.id)

        paymentValidator.forEach { it.validate(order, orderItems, command) }

        productsAccessor.updateProductStock(orderItems.map {
            ProductsAccessor.UpdateProductStockRequest(it.productId, it.quantity)
        })

        paymentProcessor.process(
            PaymentProcessor.PaymentRequest(
                orderNum = order.orderNum,
                paymentVendor = command.paymentVendor,
                paymentAmount = command.paymentAmount,
            )
        )

        return PAYMENT_PREFIX + order.id
    }
}
