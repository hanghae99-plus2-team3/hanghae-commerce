package hanghae99.plus2.team3.commerce.jaehyun.seller.domain.usecase

import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.Seller
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.SellerRepository
import hanghae99.plus2.team3.commerce.jaehyun.seller.exception.SellerNameDuplicatedException
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
        if(sellerRepository.findByName(command.name)!=null){
            throw SellerNameDuplicatedException()
        }

        return sellerRepository.save(
            Seller(
                name = command.name,
            )
        )
    }
}
