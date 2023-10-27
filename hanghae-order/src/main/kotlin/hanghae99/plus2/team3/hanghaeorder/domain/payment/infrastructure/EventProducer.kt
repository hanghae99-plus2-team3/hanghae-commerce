package hanghae99.plus2.team3.hanghaeorder.domain.payment.infrastructure

import hanghae99.plus2.team3.hanghaecommon.event.Event
import hanghae99.plus2.team3.hanghaecommon.event.EventTopic

/**
 * PaymentRequestTaskProducer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */
interface EventProducer {

    fun support(eventTopic: EventTopic): Boolean
    fun produce(event: Event)

    fun produceRollbackEvent(event: Event)
}
