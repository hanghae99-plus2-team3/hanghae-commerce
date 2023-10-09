package hanghae99.plus2.team3.hanghaeorder.domain.order.validator

import hanghae99.plus2.team3.hanghaeorder.common.exception.OrderAlreadyPayedException
import hanghae99.plus2.team3.hanghaeorder.common.exception.OrderInfoNotValidException
import hanghae99.plus2.team3.hanghaeorder.common.exception.OrderedPriceNotMatchException
import hanghae99.plus2.team3.hanghaeorder.domain.order.service.dto.OrderWithItemsDto
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase

interface PaymentValidator {
    fun validate(orderWithItems: OrderWithItemsDto, command: OrderPaymentUseCase.Command)
}

class PaymentTotalValidator : PaymentValidator {
    override fun validate(orderWithItems: OrderWithItemsDto, command: OrderPaymentUseCase.Command) {
        if (orderWithItems.orderItems.sumOf { it.productPrice * it.quantity } != command.paymentAmount) {
            throw OrderedPriceNotMatchException()
        }
    }
}

class OrderStatusValidator : PaymentValidator {
    override fun validate(orderWithItems: OrderWithItemsDto, command: OrderPaymentUseCase.Command) {
        if (orderWithItems.order.isPaymentCompleted()) {
            throw OrderAlreadyPayedException()
        }
    }
}

class PaymentRequestUserValidator : PaymentValidator {
    override fun validate(orderWithItems: OrderWithItemsDto, command: OrderPaymentUseCase.Command) {
        if (orderWithItems.order.userId != command.userId) {
            throw OrderInfoNotValidException()
        }
    }
}

class OrderItemValidator : PaymentValidator {
    override fun validate(orderWithItems: OrderWithItemsDto, command: OrderPaymentUseCase.Command) {
        if (orderWithItems.orderItems.isEmpty()) {
            throw OrderInfoNotValidException()
        }
    }
}
