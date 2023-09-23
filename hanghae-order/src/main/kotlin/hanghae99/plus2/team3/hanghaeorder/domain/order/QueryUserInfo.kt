package hanghae99.plus2.team3.hanghaeorder.domain.order

interface QueryUserInfo {
    fun query(userId: Long): UserInfo?

    data class UserInfo  (
        val userId: Long,
        val userName: String,
        val userEmail: String,
    )
}
