package hanghae99.plus2.team3.hanghaeorder.domain.payment.service

import hanghae99.plus2.team3.hanghaecommon.event.ChangeStockEvent
import hanghae99.plus2.team3.hanghaecommon.event.Event
import hanghae99.plus2.team3.hanghaecommon.event.EventStatus
import hanghae99.plus2.team3.hanghaecommon.event.StockUpdateRequest
import hanghae99.plus2.team3.hanghaeorder.common.EventProcessor
import hanghae99.plus2.team3.hanghaeorder.common.EventTransactionManager
import hanghae99.plus2.team3.hanghaeorder.common.exception.PaymentException
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderItemRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.OrderRepository
import hanghae99.plus2.team3.hanghaeorder.domain.order.service.dto.OrderWithItemsDto
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.OrderPaymentUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.order.validator.PaymentValidator
import hanghae99.plus2.team3.hanghaeorder.domain.payment.Payment
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentProcessor
import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentResultCode
import org.slf4j.Logger
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * PaymentAsyncService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */

@Service
class PaymentAsyncService(
    private val paymentValidators: List<PaymentValidator>,
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val eventProcessor: EventProcessor,
    private val eventTransactionManager: EventTransactionManager,
    private val paymentProcessor: PaymentProcessor,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val log: Logger,
) {

    @Transactional
    fun requestPaymentOf(
        orderWithItems: OrderWithItemsDto,
        command: OrderPaymentUseCase.Command,
    ) {
        validatePayment(orderWithItems, command)

        val eventId = createEventId()
        val eventsForPayments = prepareEventsForPayment(eventId, orderWithItems)

        registerEventsAndWaitTilDone(eventId, eventsForPayments)

        val eventResults = eventTransactionManager.getEventStatusForKey(eventId)

        if (eventResults!!.values.all { it == EventStatus.DONE }) {
            processPaymentsAndHandleFailure(orderWithItems, command, eventResults, eventsForPayments)
        } else {
            rollbackEvents(eventResults, eventsForPayments)
        }
    }

    private fun processPaymentsAndHandleFailure(
        orderWithItems: OrderWithItemsDto,
        command: OrderPaymentUseCase.Command,
        eventResults: ConcurrentHashMap<String, EventStatus>,
        eventsForPayments: List<Event>,
    ) {
        try {
            val payment = processPaymentOf(orderWithItems, command)
            updateOrderStatusToPaymentCompleted(orderWithItems) // 만약 order,payment 같이 묶여있다면??
            publishPaymentRequestLoggingEvent(payment)
        } catch (e: Exception) {
            val handleFailedPayment = handleFailedPayment(orderWithItems, command, e)
            rollbackEvents(eventResults, eventsForPayments)
            publishPaymentRequestLoggingEvent(handleFailedPayment)
        }
    }

    private fun prepareEventsForPayment(eventId: String, orderWithItems: OrderWithItemsDto): List<Event> {
        val changeStockEvent = createStockChangeEvent(eventId, orderWithItems)
        // couponUseEvent = createCouponUseEvent(eventId, userId, couponId......)
        return listOf(changeStockEvent)
    }

    private fun publishPaymentRequestLoggingEvent(payment: Payment) {
        applicationEventPublisher.publishEvent(payment)
    }

    private fun rollbackEvents(
        eventResults: ConcurrentHashMap<String, EventStatus>,
        eventsForPayments: List<Event>,
    ) {
        findEventsToRollback(eventResults, eventsForPayments)
            .forEach { eventProcessor.process(it) }
    }

    private fun findEventsToRollback(
        eventResults: ConcurrentHashMap<String, EventStatus>,
        eventsForPayments: List<Event>,
    ): List<Event> {
        val doneEvent = eventResults.filter { (t: String, u: EventStatus) -> u == EventStatus.DONE }
        return eventsForPayments.filter { it.subEventId in doneEvent.keys }
            .map { it.createRollbackEvent() }
    }

    private fun createEventId() = UUID.randomUUID().toString()

    private fun registerEventsAndWaitTilDone(eventId: String, events: List<Event>) {

        events.forEach { eventProcessor.process(it) }
        eventTransactionManager.addEventsToTransaction(eventId, events)
        eventTransactionManager.getCountDownLatch(eventId)?.await()
    }

    private fun createStockChangeEvent(eventId: String, orderWithItems: OrderWithItemsDto) =
        ChangeStockEvent(
            eventId = eventId,
            subEventId = UUID.randomUUID().toString(),
            stockUpdateRequests = orderWithItems.orderItems.map {
                log.info("product id : ${it.productId}, quantity : ${it.quantity}")
                StockUpdateRequest(
                    productId = it.productId,
                    updateStockCount = it.quantity
                )
            },
        )

    private fun validatePayment(
        orderWithItems: OrderWithItemsDto,
        command: OrderPaymentUseCase.Command,
    ) {
        paymentValidators.forEach { it.validate(orderWithItems, command) }
    }

    private fun processPaymentOf(
        orderWithItems: OrderWithItemsDto,
        command: OrderPaymentUseCase.Command,
    ): Payment {
        return paymentProcessor.pay(
            PaymentProcessor.PaymentRequest(
                paymentNum = orderWithItems.order.orderNum,
                paymentVendor = command.paymentVendor,
                paymentAmount = command.paymentAmount
            )
        )
    }

    private fun updateOrderStatusToPaymentCompleted(
        orderWithItems: OrderWithItemsDto,
    ) {
        orderRepository.save(orderWithItems.order.updateStatusToPaymentCompleted())
        orderWithItems.orderItems.forEach {
            orderItemRepository.save(it.updateStatusToPaymentCompleted())
        }
    }

    private fun handleFailedPayment(
        orderWithItems: OrderWithItemsDto,
        command: OrderPaymentUseCase.Command,
        e: Exception,
    ): Payment {
        return Payment.createFailPayment(
            orderNum = orderWithItems.order.orderNum,
            paymentVendor = command.paymentVendor,
            paymentAmount = command.paymentAmount,
            paymentResultCode = when (e) {
                is PaymentException -> e.paymentResultCode
                else -> PaymentResultCode.ERROR_ACCRUED_WHEN_PROCESSING_PAYMENT
            }
        )
    }
}
