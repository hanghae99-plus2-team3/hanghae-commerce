package hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure

interface UserInfoAccessor {
    fun query(token: String): UserInfo?

    data class UserInfo(
        val pk: Long,
        val name: String,
        val email: String
    )
}
