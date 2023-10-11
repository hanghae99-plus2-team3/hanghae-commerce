package hanghae99.plus2.team3.hanghaeauthuser.customer.exception

open class CustomerException(
    private val errorCode: ErrorCode
) : RuntimeException(errorCode.message)

class AlreadyExistCustomerException: CustomerException(ErrorCode.ALREADY_EXIST_CUSTOMER)
class NotExistedCustomerException: CustomerException(ErrorCode.NOT_EXISTED_CUSTOMER)
