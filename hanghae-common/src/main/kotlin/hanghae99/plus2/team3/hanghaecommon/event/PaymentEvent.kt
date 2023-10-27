package hanghae99.plus2.team3.hanghaecommon.event

/**
 * PaymentEvent
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */
data class PaymentEvent(
    override val eventId: String,
    override val subEventId: String,
    override val status: EventStatus = EventStatus.READY,
    val paymentNum: String,
    val paymentVendor: String,
    val paymentAmount: Long,
) : Event(
    eventId = eventId,
    subEventId = subEventId,
    topic = EventTopic.PAYMENT_EVENT,
    status = status,
)

