package hanghae99.plus2.team3.hanghaeproduct

import hanghae99.plus2.team3.hanghaecommon.event.ChangeStockEvent
import hanghae99.plus2.team3.hanghaecommon.event.EventStatus
import hanghae99.plus2.team3.hanghaecommon.event.EventTopic
import org.slf4j.Logger
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

/**
 * ProductStockChangeDoneProducer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */

@Component
class ProductStockChangeDoneProducer(
    private val kafkaTemplate: KafkaTemplate<String, ChangeStockEvent>,
    private val log: Logger
) {
    fun produce(event: ChangeStockEvent) {
        val successEvent = (event).copy(status = EventStatus.DONE)
        log.info(successEvent.status.name + " =========================")
        kafkaTemplate.send(
            EventTopic.PRODUCT_STOCK_CHANGE_DONE_EVENT.event,
            successEvent
        )
    }
}
