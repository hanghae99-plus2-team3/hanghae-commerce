package hanghae99.plus2.team3.commerce.jaehyun.product.exception

import hanghae99.plus2.team3.commerce.jaehyun.common.exception.ErrorCode

open class ProductException(
    errorCode: ErrorCode
) : RuntimeException(errorCode.message)

class InvalidShopInfoException : ProductException(ErrorCode.INVALID_SHOP_INFO)
