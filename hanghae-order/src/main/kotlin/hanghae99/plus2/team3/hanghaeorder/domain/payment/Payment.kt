package hanghae99.plus2.team3.hanghaeorder.domain.payment

data class Payment(
    val id: Long,
    val orderNum: String,
    val paymentVendor: PaymentVendor,
    val paymentAmount: Long,
    val success: Boolean,
    val paymentResultCode: PaymentResultCode
) {
    companion object {
        fun createSuccessPayment(
            paymentNum: String,
            paymentVendor: PaymentVendor,
            paymentAmount: Long
        ): Payment {
            return Payment(
                id = 0L,
                orderNum = paymentNum,
                paymentVendor = paymentVendor,
                paymentAmount = paymentAmount,
                success = true,
                paymentResultCode = PaymentResultCode.PAYMENT_SUCCESS
            )
        }

        fun createFailPayment(
            paymentNum: String,
            paymentVendor: PaymentVendor,
            paymentAmount: Long,
            paymentResultCode: PaymentResultCode
        ): Payment {
            return Payment(
                id = 0L,
                orderNum = paymentNum,
                paymentVendor = paymentVendor,
                paymentAmount = paymentAmount,
                success = false,
                paymentResultCode = paymentResultCode
            )
        }

        fun createSuccessRefund(
            id: Long,
            paymentNum: String,
            paymentVendor: PaymentVendor,
            paymentAmount: Long
        ): Payment {
            return Payment(
                id = id,
                orderNum = paymentNum,
                paymentVendor = paymentVendor,
                paymentAmount = paymentAmount,
                success = true,
                paymentResultCode = PaymentResultCode.REFUND_SUCCESS
            )
        }

        fun createFailRefund(
            id: Long,
            paymentNum: String,
            paymentVendor: PaymentVendor,
            paymentAmount: Long,
            paymentResultCode: PaymentResultCode
        ): Payment {
            return Payment(
                id = id,
                orderNum = paymentNum,
                paymentVendor = paymentVendor,
                paymentAmount = paymentAmount,
                success = false,
                paymentResultCode = paymentResultCode
            )
        }
    }
}
