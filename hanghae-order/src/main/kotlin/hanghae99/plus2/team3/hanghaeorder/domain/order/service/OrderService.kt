package hanghae99.plus2.team3.hanghaeorder.domain.order.service

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.PaymentValidator
import hanghae99.plus2.team3.hanghaeorder.exception.OrderNotFoundException
import hanghae99.plus2.team3.hanghaeorder.exception.OrderedUserNotFoundException
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
    private val userInfoAccessor: UserInfoAccessor,
    private val productsAccessor: ProductsAccessor,
    private val paymentValidators: List<PaymentValidator>,
    private val paymentProcessor: PaymentProcessor,
) {

    fun makePaymentForOder(command: OrderPaymentUseCase.Command) : String {
        if (userInfoAccessor.query(command.userId) == null)
            throw OrderedUserNotFoundException()

        val order = orderRepository.findByOrderNum(command.orderNum)?: throw OrderNotFoundException()
        val orderItems = orderItemRepository.findByOrderId(order.id)

        paymentValidators.forEach { it.validate(order, orderItems, command) }

        productsAccessor.updateProductStock(
            orderItems.map {
                ProductsAccessor.UpdateProductStockRequest(it.productId, it.quantity)
            }
        )

        paymentProcessor.pay(
            PaymentProcessor.PaymentRequest(
                orderNum = order.orderNum,
                paymentVendor = command.paymentVendor,
                paymentAmount = command.paymentAmount,
            )
        )

        return order.getPaymentNum()
    }

}
