package hanghae99.plus2.team3.hanghaeauthuser.auth

data class RegisterMemberRequest(
    val id: String,
    val pw: String
)

data class RegisterMemberResponse(
    val token: String
)

data class LoginRequest(
    val id: String,
    val pw: String
)

data class LoginResponse(
    val token: String
)

data class AuthTokenInfoResponse(
    val memberPk: Long
) {
    init {
        require(memberPk > 0)
    }
}
