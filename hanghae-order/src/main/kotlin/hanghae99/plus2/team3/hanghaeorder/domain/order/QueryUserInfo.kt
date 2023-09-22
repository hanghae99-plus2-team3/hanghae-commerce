package hanghae99.plus2.team3.hanghaeorder.domain.order

interface QueryUserInfo {
    fun query(userId: Long): UserInfo?

    data class UserInfo private constructor(
        val userId: Long,
        val userName: String,
        val userEmail: String,
    ) {

        companion object {
            fun create(
                userId: Long,
                userName: String,
                userEmail: String,
            ): UserInfo {

                return UserInfo(
                    userId = userId,
                    userName = userName,
                    userEmail = userEmail,
                )
            }
        }
    }

}
