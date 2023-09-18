package hanghae99.plus2.team3.commerce.jaehyun.seller.exception

import hanghae99.plus2.team3.commerce.jaehyun.common.exception.ErrorCode

/**
 * SellerException
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/18
 */
open class SellerException(
    errorCode: ErrorCode
) : RuntimeException(errorCode.message)

class SellerNotFoundException : SellerException(ErrorCode.SELLER_NOT_FOUND)


