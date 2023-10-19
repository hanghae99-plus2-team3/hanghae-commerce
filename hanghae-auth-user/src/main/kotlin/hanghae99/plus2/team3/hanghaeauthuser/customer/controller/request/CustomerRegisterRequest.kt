package hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request

import hanghae99.plus2.team3.hanghaeauthuser.auth.RegisterMemberRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.domain.CustomerEntity

data class CustomerRegisterRequest(
    val loginId: String,
    val password: String,
    val name: String,
    val email: String
) {
    init {
        require(loginId.length in 8..20) {
            "로그인ID는 8자 이상 20자 이하로 입력해주세요."
        }
        require(password.length in 8 .. 20) {
            "비밀번호는 8자 이상 20자 이하로 입력해주세요."
        }
        require(name.length in 2 .. 10) {
            "이름은 2자 이상 10자 이하로 입력해주세요."
        }
    }
}

fun CustomerRegisterRequest.toEntity(): CustomerEntity {
    return CustomerEntity(0L, name, email)
}

fun CustomerRegisterRequest.toAuthRequest(): RegisterMemberRequest {
    return RegisterMemberRequest(loginId, password)
}
