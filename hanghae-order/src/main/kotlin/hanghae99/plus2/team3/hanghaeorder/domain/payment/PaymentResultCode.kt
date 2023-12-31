package hanghae99.plus2.team3.hanghaeorder.domain.payment

/**
 * PaymentResultCode
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/10/01
 */
enum class PaymentResultCode(
    val code: String,
    val message: String
) {

    PAYMENT_SUCCESS("200", "결제 성공"),
    REFUND_SUCCESS("201", "환불 성공"),
    NOT_SUPPORTED_PAYMENT_VENDOR("401", "지원하지 않는 결제사 입니다."),
    ERROR_ACCRUED_WHEN_PROCESSING_PAYMENT("402", "결제 처리중 오류가 발생했습니다."),
    TIMEOUT_WHEN_PROCESSING_PAYMENT("403", "결제 처리시간이 초과되었습니다."),
    PAYMENT_NOT_FOUND("404", "결제 정보를 찾을 수 없습니다."),
    ERROR_ACCRUED_WHEN_PROCESSING_REFUND("402", "환불 처리중 오류가 발생했습니다."),
    TIMEOUT_WHEN_PROCESSING_REFUND("403", "환불 처리시간이 초과되었습니다.")
}
