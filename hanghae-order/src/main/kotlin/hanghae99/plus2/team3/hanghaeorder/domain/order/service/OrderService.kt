package hanghae99.plus2.team3.hanghaeorder.domain.order.service

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.RegisterOrderUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.PaymentValidator
import hanghae99.plus2.team3.hanghaeorder.exception.OrderNotFoundException
import hanghae99.plus2.team3.hanghaeorder.exception.PaymentProcessException
import hanghae99.plus2.team3.hanghaeorder.exception.ProductNotFoundException
import hanghae99.plus2.team3.hanghaeorder.exception.ProductStockNotEnoughException
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
    private val paymentProcessor: PaymentProcessor,
) {

    fun makeOrder(command: RegisterOrderUseCase.Command): String {
        validateOrderedProducts(command, getProductInfo(command))

        val savedOrder = orderRepository.save(command.toDomain())

        command.orderItemList.forEach {
            orderItemRepository.save(it.toDomain(order = savedOrder))
        }

        return savedOrder.orderNum
    }

    fun makePaymentForOder(command: OrderPaymentUseCase.Command) : String {
        val order = orderRepository.findByOrderNum(command.orderNum)?: throw OrderNotFoundException()
        val orderItems = orderItemRepository.findByOrderId(order.id)

        paymentValidators.forEach { it.validate(order, orderItems, command) }

        productsAccessor.updateProductStock(
            orderItems.map {
                ProductsAccessor.UpdateProductStockRequest(it.productId, it.quantity)
            }
        )

        try{
            paymentProcessor.pay(
                PaymentProcessor.PaymentRequest(
                    orderNum = order.orderNum,
                    paymentVendor = command.paymentVendor,
                    paymentAmount = command.paymentAmount,
                )
            )
        } catch (e: Exception) {
            productsAccessor.updateProductStock(
                orderItems.map {
                    ProductsAccessor.UpdateProductStockRequest(it.productId, -it.quantity)
                }
            )
            throw PaymentProcessException()
        }

        return order.getPaymentNum()
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


}
