package hanghae99.plus2.team3.hanghaeorder.common

import hanghae99.plus2.team3.hanghaecommon.event.Event
import hanghae99.plus2.team3.hanghaecommon.event.EventStatus
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch

/**
 * CountDownLatchManager
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */

@Component
class EventTransactionManager(
    private val countDownLatchMap: ConcurrentHashMap<String, CountDownLatch> = ConcurrentHashMap(),
    private val eventMap: ConcurrentHashMap<String, ConcurrentHashMap<String, EventStatus>> = ConcurrentHashMap(),
) {

    fun addEventsToTransaction(eventId: String, events: List<Event>) {
        this.countDownLatchMap[eventId] = CountDownLatch(events.size)
        this.eventMap[eventId] =
            ConcurrentHashMap(
                events.associate {
                    it.subEventId to it.status
                }
            )
    }

    fun getCountDownLatch(eventId: String): CountDownLatch? {
        return this.countDownLatchMap[eventId]
    }

    fun setEventStatusForKey(
        eventId: String,
        subEventId: String,
        status: EventStatus,
    ) {
        val eventStatus = this.eventMap[eventId]
        eventStatus?.set(subEventId, status)
    }

    fun getEventStatusForKey(eventId: String): ConcurrentHashMap<String, EventStatus>? {
        return this.eventMap[eventId]
    }
}
