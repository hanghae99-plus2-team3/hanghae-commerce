package hanghae99.plus2.team3.hanghaeauthuser.auth

data class AuthTokenInfoResponse(
    val memberPk: Long
) {
    init {
        require(memberPk > 0)
    }
}
