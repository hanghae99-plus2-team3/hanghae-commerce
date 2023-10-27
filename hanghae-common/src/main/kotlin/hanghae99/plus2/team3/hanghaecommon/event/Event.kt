package hanghae99.plus2.team3.hanghaecommon.event

/**
 * EventTemplate
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/25/23
 */
abstract class Event(
    open val eventId: String,
    open val subEventId: String,
    open val topic: EventTopic,
    open val status: EventStatus,
) {
    abstract fun createRollbackEvent(): Event
}

enum class EventStatus {
    READY, DONE, NOT_ENOUGH_STOCK
}
