package hanghae99.plus2.team3.hanghaeorder.domain.order.validator

import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.common.exception.OrderAlreadyPayedException
import hanghae99.plus2.team3.hanghaeorder.common.exception.OrderInfoNotValidException
import hanghae99.plus2.team3.hanghaeorder.common.exception.OrderedPriceNotMatchException

interface PaymentValidator {
    fun validate(order: Order, orderItems: List<OrderItem>, command: OrderPaymentUseCase.Command)
}


class PaymentTotalValidator : PaymentValidator {
    override fun validate(order: Order, orderItems: List<OrderItem>, command: OrderPaymentUseCase.Command) {
        if (orderItems.sumOf { it.productPrice * it.quantity } != command.paymentAmount)
            throw OrderedPriceNotMatchException()
    }
}

class OrderStatusValidator : PaymentValidator {
    override fun validate(order: Order, orderItems: List<OrderItem>, command: OrderPaymentUseCase.Command) {
        if (order.isPaymentCompleted())
            throw OrderAlreadyPayedException()
    }
}

class PaymentRequestUserValidator: PaymentValidator {
    override fun validate(order: Order, orderItems: List<OrderItem>, command: OrderPaymentUseCase.Command) {
        if (order.userId != command.userId)
            throw OrderInfoNotValidException()
    }
}

class OrderItemValidator : PaymentValidator {
    override fun validate(order: Order, orderItems: List<OrderItem>, command: OrderPaymentUseCase.Command) {
        if (orderItems.isEmpty())
            throw OrderInfoNotValidException()
    }
}
