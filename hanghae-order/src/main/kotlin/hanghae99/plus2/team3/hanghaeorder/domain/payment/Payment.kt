package hanghae99.plus2.team3.hanghaeorder.domain.payment

data class Payment(
    val id : Long,
    val paymentNum: String,
    val paymentVendor: PaymentVendor,
    val paymentAmount: Long,
    val success: Boolean,
    val paymentResultCode: PaymentResultCode,
){
    companion object{
        fun createSuccessPayment(
            paymentNum: String,
            paymentVendor: PaymentVendor,
            paymentAmount: Long,
        ): Payment {
            return Payment(
                id = 0L,
                paymentNum = paymentNum,
                paymentVendor = paymentVendor,
                paymentAmount = paymentAmount,
                success = true,
                paymentResultCode = PaymentResultCode.PAYMENT_SUCCESS,
            )
        }

        fun createFailPayment(
            paymentNum: String,
            paymentVendor: PaymentVendor,
            paymentAmount: Long,
            paymentResultCode: PaymentResultCode,
        ): Payment {
            return Payment(
                id = 0L,
                paymentNum = paymentNum,
                paymentVendor = paymentVendor,
                paymentAmount = paymentAmount,
                success = false,
                paymentResultCode = paymentResultCode,
            )
        }
    }
}
