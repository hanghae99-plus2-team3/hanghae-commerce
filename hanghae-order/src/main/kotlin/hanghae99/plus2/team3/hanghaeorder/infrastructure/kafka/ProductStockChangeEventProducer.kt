package hanghae99.plus2.team3.hanghaeorder.infrastructure.kafka

import hanghae99.plus2.team3.hanghaecommon.event.ChangeStockEvent
import hanghae99.plus2.team3.hanghaecommon.event.Event
import hanghae99.plus2.team3.hanghaecommon.event.EventTopic
import hanghae99.plus2.team3.hanghaeorder.domain.payment.infrastructure.EventProducer
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

/**
 * ProductStockChangeEventProducerImpl
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */

@Component
class ProductStockChangeEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, ChangeStockEvent>,
) : EventProducer {

    override fun support(eventTopic: EventTopic) = eventTopic == EventTopic.PRODUCT_STOCK_CHANGE_EVENT


    override fun produce(event: Event) {
        kafkaTemplate.send(
            EventTopic.PRODUCT_STOCK_CHANGE_EVENT.event,
            event as ChangeStockEvent
        )
    }

    override fun produceRollbackEvent(event: Event) {
        TODO("Not yet implemented")
    }
}
