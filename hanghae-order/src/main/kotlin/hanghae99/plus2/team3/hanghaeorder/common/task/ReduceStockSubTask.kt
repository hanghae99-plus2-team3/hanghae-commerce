package hanghae99.plus2.team3.hanghaeorder.common.task

/**
 * ReduceStockSubTask
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */
data class ReduceStockSubTask(
    val stockUpdateRequests: List<StockUpdateRequest>,
    val taskType: TaskType,
    val status: String,
)

data class StockUpdateRequest(
    val productId: Long,
    val updateStockCount: Int
)

enum class TaskType {
    REDUCE_STOCK
}
