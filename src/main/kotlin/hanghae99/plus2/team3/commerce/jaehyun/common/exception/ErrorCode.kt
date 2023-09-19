package hanghae99.plus2.team3.commerce.jaehyun.common.exception

/**
 * ErrorCode
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/18
 */
enum class ErrorCode  (
    val message: String,
){
    SELLER_NOT_FOUND(message = "등록되지 않은 판매자 ID 입니다."),
    SELLER_NAME_DUPLICATED(message = "이미 등록된 판매자명 입니다."),
    ;
}
