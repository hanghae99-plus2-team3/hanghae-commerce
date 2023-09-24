package hanghae99.plus2.team3.hanghaeorder.exception

enum class ErrorCode(
    val message: String,
) {
    NOT_ENOUGH_STOCK("주문할 상품의 재고가 부족합니다."),
    PRODUCT_NOT_FOUND("주문할 상품이 존재하지 않습니다."),
    USER_NOT_FOUND("존재하지 않는 고객의 주문입니다.")

    ;
}
