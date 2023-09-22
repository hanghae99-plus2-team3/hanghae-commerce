package hanghae99.plus2.team3.hanghaeorder.exception

open class OrderException(
    errorCode: ErrorCode
) : RuntimeException(errorCode.message)


class ProductStockNotEnoughException : OrderException(ErrorCode.NOT_ENOUGH_STOCK)
class ProductNotFoundException : OrderException(ErrorCode.PRODUCT_NOT_FOUND)
class OrderedUserNotFoundException : OrderException(ErrorCode.USER_NOT_FOUND)


