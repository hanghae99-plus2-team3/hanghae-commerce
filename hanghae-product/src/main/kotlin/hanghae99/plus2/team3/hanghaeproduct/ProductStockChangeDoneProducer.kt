package hanghae99.plus2.team3.hanghaeproduct

import hanghae99.plus2.team3.hanghaecommon.task.ChangeStockEvent
import hanghae99.plus2.team3.hanghaecommon.task.ReduceStockStatus
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
) {
    fun produce(event: ChangeStockEvent) {
        val successEvent = event.copy(status = ReduceStockStatus.DONE)

        kafkaTemplate.send(
            EventTopic.PRODUCT_STOCK_CHANGE_DONE_EVENT.event,
            successEvent
        )
    }
}
