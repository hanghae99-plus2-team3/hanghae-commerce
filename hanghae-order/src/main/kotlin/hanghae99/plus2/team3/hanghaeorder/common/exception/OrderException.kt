package hanghae99.plus2.team3.hanghaeorder.common.exception

open class OrderException(
    private val errorCode: ErrorCode
) : RuntimeException(errorCode.message)

class AuthenticationException : OrderException(ErrorCode.AUTHENTICATION_FAILED)
class ProductStockNotEnoughException : OrderException(ErrorCode.NOT_ENOUGH_STOCK)
class ProductNotFoundException : OrderException(ErrorCode.PRODUCT_NOT_FOUND)
class OrderNotFoundException : OrderException(ErrorCode.ORDER_NOT_FOUND)
class OrderInfoNotValidException : OrderException(ErrorCode.ORDER_INFO_NOT_VALID)
class OrderedPriceNotMatchException : OrderException(ErrorCode.ORDER_PRICE_NOT_MATCH)
class OrderedItemOutOfStockException : OrderException(ErrorCode.ORDERED_ITEM_OUT_OF_STOCK)
class OrderAlreadyPayedException : OrderException(ErrorCode.ORDER_ALREADY_PAYED)
