package hanghae99.plus2.team3.commerce.jaehyun.shop.exception

import hanghae99.plus2.team3.commerce.jaehyun.common.exception.ErrorCode
import hanghae99.plus2.team3.commerce.jaehyun.seller.exception.SellerException

/**
 * ShopException
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/19
 */
open class ShopException(
    errorCode: ErrorCode
) : RuntimeException(errorCode.message)
class ShopNameDuplicatedException : ShopException(ErrorCode.SHOP_NAME_DUPLICATED)
