package hanghae99.plus2.team3.hanghaeauthuser.customer.controller.response

import hanghae99.plus2.team3.hanghaeauthuser.customer.domain.CustomerEntity

data class CustomerInfoResponse(
    val pk: Long,
    val name: String,
    val email: String
) {
    companion object {
        fun create(customerEntity: CustomerEntity): CustomerInfoResponse {
            return CustomerInfoResponse(
                pk = customerEntity.pk,
                name = customerEntity.name,
                email = customerEntity.email
            )
        }
    }
}
