package hanghae99.plus2.team3.hanghaeauthuser.auth

data class RegisterMemberRequest(
    val loginId: String,
    val pw: String
)

data class LoginRequest(
    val id: String,
    val pw: String
)

data class AuthTokenInfoResponse(
    val memberPk: Long
) {
    init {
        require(memberPk > 0)
    }
}
