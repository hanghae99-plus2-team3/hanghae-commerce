package hanghae99.plus2.team3.hanghaeorder.common

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
class CountDownLatchManager(
    private val countDownLatchMap: ConcurrentHashMap<String, CountDownLatch> = ConcurrentHashMap(),
    private val stringMap: ConcurrentHashMap<String, String> = ConcurrentHashMap(),
) {

    fun addCountDownLatch(key: String) {
        this.countDownLatchMap[key] = CountDownLatch(1)
    }

    fun getCountDownLatch(key: String): CountDownLatch? {
        return this.countDownLatchMap[key]
    }

    fun setDataForKey(key: String, data: String) {
        this.stringMap[key] = data
    }

    fun getDataForKey(key: String): String? {
        return this.stringMap[key]
    }
}
