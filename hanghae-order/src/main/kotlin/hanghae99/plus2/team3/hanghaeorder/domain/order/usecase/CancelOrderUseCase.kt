package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase

interface CancelOrderUseCase {
    fun command(command: Command): String

    data class Command(
        val orderNum: String,
        val userId: Long,
    )
}
