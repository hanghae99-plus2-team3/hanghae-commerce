package hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request

data class CustomerLoginRequest(
    val loginId: String,
    val password: String
) {
    init {
        require(loginId.length in 8..20) {
            "로그인ID는 8자 이상 20자 이하로 입력해주세요."
        }
        require(password.length in 8..20) {
            "비밀번호는 8자 이상 20자 이하로 입력해주세요."
        }
    }
}
