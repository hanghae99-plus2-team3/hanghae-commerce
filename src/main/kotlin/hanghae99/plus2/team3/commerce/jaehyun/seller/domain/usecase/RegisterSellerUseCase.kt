package hanghae99.plus2.team3.commerce.jaehyun.seller.domain.usecase

import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.Seller
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.SellerRepository
import org.springframework.stereotype.Component

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

@Component
internal class RegisterSellerUseCaseImpl(
    private val sellerRepository: SellerRepository
) : RegisterSellerUseCase {

    override fun command(command: RegisterSellerUseCase.Command): Seller {
        val seller = Seller(
            name = command.name,
        )
        return sellerRepository.save(seller)
    }
}
