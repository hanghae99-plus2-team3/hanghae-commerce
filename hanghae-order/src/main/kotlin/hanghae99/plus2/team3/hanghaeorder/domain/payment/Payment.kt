package hanghae99.plus2.team3.hanghaeorder.domain.payment

data class Payment(
    val id : Long,
    val paymentNum: String,
    val paymentVendor: PaymentVendor,
    val paymentAmount: Long,
    val success: Boolean,
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
            )
        }

        fun createFailPayment(
            paymentNum: String,
            paymentVendor: PaymentVendor,
            paymentAmount: Long,
        ): Payment {
            return Payment(
                id = 0L,
                paymentNum = paymentNum,
                paymentVendor = paymentVendor,
                paymentAmount = paymentAmount,
                success = false,
            )
        }
    }
}
