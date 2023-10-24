package hanghae99.plus2.team3.hanghaeorder.domain.payment.service

import hanghae99.plus2.team3.hanghaeorder.common.CountDownLatchManager
import hanghae99.plus2.team3.hanghaeorder.common.exception.OrderAlreadyPayedException
import hanghae99.plus2.team3.hanghaeorder.common.task.PaymentTask
import hanghae99.plus2.team3.hanghaeorder.common.task.ReduceStockSubTask
import hanghae99.plus2.team3.hanghaeorder.common.task.StockUpdateRequest
import hanghae99.plus2.team3.hanghaeorder.common.task.TaskType
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.payment.infrastructure.PaymentRequestTaskProducer
import org.springframework.stereotype.Service
import java.util.*

/**
 * PaymentAsyncService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */

@Service
class PaymentAsyncService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val paymentRequestTaskProducer: PaymentRequestTaskProducer,
    private val countDownLatchManager: CountDownLatchManager,
) {

    fun requestOrderPayment(command: OrderPaymentUseCase.Command) {
        val order = orderRepository.getByOrderNumAndUserId(command.orderNum, command.userId)
        if (order.isPaymentCompleted())
            throw OrderAlreadyPayedException()

        val orderedItems = orderItemRepository.findByOrderId(order.id)


        val reduceStockSubTask = ReduceStockSubTask(
            stockUpdateRequests = orderedItems.map {
                StockUpdateRequest(
                    productId = it.productId,
                    updateStockCount = it.quantity
                )
            },
            taskType = TaskType.REDUCE_STOCK,
            status = "READY"
        )

        val paymentTask = PaymentTask(
            taskId = UUID.randomUUID().toString(),
            userId = command.userId,
            reduceStockSubTaskList = listOf(reduceStockSubTask),
            paymentNum = order.orderNum,
            paymentVendor = command.paymentVendor,
            paymentAmount = command.paymentAmount
        )

        paymentRequestTaskProducer.produce(paymentTask)

        countDownLatchManager.getCountDownLatch(
            "PAYMENT-TASK" + UUID.randomUUID().toString().substring(0, 8)
        )?.await()

    }
}
