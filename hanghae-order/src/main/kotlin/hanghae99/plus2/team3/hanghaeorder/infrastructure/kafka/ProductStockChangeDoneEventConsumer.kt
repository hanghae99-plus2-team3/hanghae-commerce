package hanghae99.plus2.team3.hanghaeorder.infrastructure.kafka

import hanghae99.plus2.team3.hanghaecommon.event.ChangeStockEvent
import hanghae99.plus2.team3.hanghaeorder.common.EventTransactionManager
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ProductStockChangeDoneEventConsumer(
    private val eventTransactionManager: EventTransactionManager,
) {
    @KafkaListener(topics = ["product-stock-change-done"], groupId = "order-domain-group")
    fun listener(event: ChangeStockEvent) {
        println("ProductStockChangeDoneEventConsumer : $event")
        Thread.sleep(10000)
        eventTransactionManager.setEventStatusForKey(
            eventId = event.eventId,
            subEventId = event.subEventId,
            status = event.status
        )
        eventTransactionManager.getCountDownLatch(event.eventId)?.countDown()
    }
}
