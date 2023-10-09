package hanghae99.plus2.team3.hanghaeorder.common.exception

import hanghae99.plus2.team3.hanghaeorder.domain.payment.PaymentResultCode

/**
 * PaymentException
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/10/01
 */
open class PaymentException(
    val paymentResultCode: PaymentResultCode
) : RuntimeException(paymentResultCode.message)

class PaymentProcessException(paymentResultCode: PaymentResultCode) : PaymentException(paymentResultCode)
class NotSupportedPaymentVendorException : PaymentException(PaymentResultCode.NOT_SUPPORTED_PAYMENT_VENDOR)
class PaymentTimeOutException : PaymentException(PaymentResultCode.TIMEOUT_WHEN_PROCESSING_PAYMENT)
class PaymentNotFoundException : PaymentException(PaymentResultCode.PAYMENT_NOT_FOUND)
class PaymentRefundProcessException(paymentResultCode: PaymentResultCode) : PaymentException(paymentResultCode)
