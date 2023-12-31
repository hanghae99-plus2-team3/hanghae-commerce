package hanghae99.plus2.team3.hanghaeorder.common.exception

enum class ErrorCode(
    val message: String
) {
    NOT_ENOUGH_STOCK("주문할 상품의 재고가 부족합니다."),
    PRODUCT_NOT_FOUND("주문할 상품이 존재하지 않습니다."),
    ORDER_NOT_FOUND("존재하지 않는 주문입니다."),
    ORDER_INFO_NOT_VALID("주문 정보가 유효하지 않습니다."),
    ORDER_PRICE_NOT_MATCH("주문 금액이 일치하지 않습니다."),
    ORDERED_ITEM_OUT_OF_STOCK("주문한 상품중 재고가 부족한 상품이 있습니다."),
    ORDER_ALREADY_PAYED("이미 결제 완료된 주문입니다."),
    AUTHENTICATION_FAILED("사용자 인증에 실패했습니다."),
    ORDER_PRODUCT_STARTED_DELIVERY("배송 중인 상품은 취소할 수 없습니다.")
    ;
}
