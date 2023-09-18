package hanghae99.plus2.team3.commerce.seller.domain.usecase

import hanghae99.plus2.team3.commerce.seller.domain.Seller

interface RegisterSellerUseCase {
    fun command(command: Command): Seller

    data class Command(
        val name: String,
    ) {
        init {
            require(name.isNotBlank()) { "판매자 이름은 필수입니다." }
        }
    }
}
