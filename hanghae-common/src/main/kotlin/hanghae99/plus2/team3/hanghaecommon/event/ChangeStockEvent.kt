package hanghae99.plus2.team3.hanghaecommon.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serializer
import org.springframework.stereotype.Component


/**
 * ReduceStockEvent
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */
data class ChangeStockEvent(
    override val eventId: String,
    override val subEventId: String,
    override val status: EventStatus = EventStatus.READY,
    val stockUpdateRequests: List<StockUpdateRequest>,
) : Event(
    eventId = eventId,
    subEventId = subEventId,
    topic = EventTopic.PRODUCT_STOCK_CHANGE_EVENT,
    status = status,
) {
    override fun createRollbackEvent() = RollbackChangeStockEvent(this)
}

data class RollbackChangeStockEvent(
    override val eventId: String,
    override val subEventId: String,
    override val status: EventStatus = EventStatus.READY,
    val stockUpdateRequests: List<StockUpdateRequest>,
) : Event(
    eventId = eventId,
    subEventId = subEventId,
    topic = EventTopic.PRODUCT_STOCK_CHANGE_EVENT,
    status = status,
) {
    constructor(changeStockEvent: ChangeStockEvent)
        : this(
        eventId = changeStockEvent.eventId,
        subEventId = changeStockEvent.subEventId,
        status = EventStatus.READY,
        stockUpdateRequests = changeStockEvent.stockUpdateRequests
    )

    override fun createRollbackEvent() = this

}

data class StockUpdateRequest(
    val productId: Long,
    val updateStockCount: Int,
)

@Component
class ChangeStockEventDeserializer : Deserializer<ChangeStockEvent> {

    private val objectMapper = ObjectMapper().registerModule(KotlinModule())
    override fun deserialize(topic: String?, data: ByteArray?): ChangeStockEvent {
        return objectMapper.readValue(String(data!!, Charsets.UTF_8), ChangeStockEvent::class.java)
    }
}

@Component
class ChangeStockEventSerializer : Serializer<ChangeStockEvent> {
    private val objectMapper = ObjectMapper().registerModule(KotlinModule())
    override fun serialize(topic: String?, data: ChangeStockEvent?): ByteArray {
        return objectMapper.writeValueAsBytes(data)
    }

}
