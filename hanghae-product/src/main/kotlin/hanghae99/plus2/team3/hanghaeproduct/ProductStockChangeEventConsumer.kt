package hanghae99.plus2.team3.hanghaeproduct

import hanghae99.plus2.team3.hanghaecommon.event.ChangeStockEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

/**
 * TaskConsumer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */
@Component
class ProductStockChangeEventConsumer(
    private val productStockChangeDoneProducer: ProductStockChangeDoneProducer,
) {
    @KafkaListener(topics = ["product-stock-change"], groupId = "product-domain-group")
    fun listener(event: ChangeStockEvent) {
        println("ProductStockChangeEventConsumer : $event")
        productStockChangeDoneProducer.produce(event)
    }
}
