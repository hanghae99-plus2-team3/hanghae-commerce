package hanghae99.plus2.team3.hanghaeproduct

open class ProductException (
    private val errorCode: ErrorCode
) : Throwable(errorCode.message) {
    class ProductNotFoundException: ProductException(ErrorCode.PRODUCT_NOT_FOUND)
}


