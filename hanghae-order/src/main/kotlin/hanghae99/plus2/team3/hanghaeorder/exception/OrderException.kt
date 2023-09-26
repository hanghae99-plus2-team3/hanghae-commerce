package hanghae99.plus2.team3.hanghaeorder.exception

open class OrderException(
    errorCode: ErrorCode
) : RuntimeException(errorCode.message)


class ProductStockNotEnoughException : OrderException(ErrorCode.NOT_ENOUGH_STOCK)
class ProductNotFoundException : OrderException(ErrorCode.PRODUCT_NOT_FOUND)
class OrderedUserNotFoundException : OrderException(ErrorCode.USER_NOT_FOUND)
class OrderNotFoundException : OrderException(ErrorCode.ORDER_NOT_FOUND)
class OrderInfoNotValidException : OrderException(ErrorCode.ORDER_INFO_NOT_VALID)
class OrderedPriceNotMatchException : OrderException(ErrorCode.ORDER_PRICE_NOT_MATCH)
class OrderedItemOutOfStockException : OrderException(ErrorCode.ORDERED_ITEM_OUT_OF_STOCK)
class OrderAlreadyPayedException : OrderException(ErrorCode.ORDER_ALREADY_PAYED)
class PaymentProcessException : OrderException(ErrorCode.ERROR_ACCRUED_WHEN_PROCESSING_PAYMENT)
class NotSupportedPaymentVendorException : OrderException(ErrorCode.NOT_SUPPORTED_PAYMENT_VENDOR)
