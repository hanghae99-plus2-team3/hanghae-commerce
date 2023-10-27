package hanghae99.plus2.team3.hanghaecommon.event

/**
 * EventTopic
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */
enum class EventTopic(val event: String) {

    PRODUCT_STOCK_CHANGE_EVENT("product-stock-change"),
    PRODUCT_STOCK_CHANGE_DONE_EVENT("product-stock-change-done"),
    PAYMENT_EVENT("payment"),


}
