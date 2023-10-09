package hanghae99.plus2.team3.hanghaeauthuser.customer.exception

enum class ErrorCode(
    val message: String
) {
    ALREADY_EXIST_CUSTOMER("이미 가입된 사용자입니다.")
    ;
}
