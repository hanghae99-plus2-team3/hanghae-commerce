package hanghae99.plus2.team3.hanghaeorder.domain.order.service.dto

import hanghae99.plus2.team3.hanghaeorder.domain.order.Order
import hanghae99.plus2.team3.hanghaeorder.domain.order.OrderItem

/**
 * OrderItemsDto
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/9/23
 */
data class OrderWithItemsDto(
    val order: Order,
    val orderItems: List<OrderItem>
)
