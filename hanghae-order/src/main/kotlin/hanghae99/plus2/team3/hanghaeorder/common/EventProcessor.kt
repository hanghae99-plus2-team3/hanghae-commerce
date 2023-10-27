package hanghae99.plus2.team3.hanghaeorder.common

import hanghae99.plus2.team3.hanghaecommon.event.Event
import hanghae99.plus2.team3.hanghaecommon.event.EventTopic
import hanghae99.plus2.team3.hanghaeorder.domain.payment.infrastructure.EventProducer
import org.springframework.stereotype.Component

/**
 * EventProcessor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/27/23
 */

@Component
class EventProcessor(
    private val eventProducers: List<EventProducer>,
) {
    fun process(event: Event) = findEventProducer(event.topic).produce(event)

    private fun findEventProducer(eventTopic: EventTopic) =
        eventProducers.find { it.support(eventTopic) }
            ?: throw IllegalArgumentException("EventProducer not found for $eventTopic")

}
