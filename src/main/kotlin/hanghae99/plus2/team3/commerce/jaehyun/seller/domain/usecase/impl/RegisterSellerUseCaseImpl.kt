package hanghae99.plus2.team3.commerce.jaehyun.seller.domain.usecase.impl

import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.usecase.RegisterSellerUseCase
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.Seller
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.SellerRepository

class RegisterSellerUseCaseImpl(
    private val sellerRepository: SellerRepository
) : RegisterSellerUseCase {

    override fun command(command: RegisterSellerUseCase.Command): Seller {
        val seller = Seller(
            name = command.name,
        )
        return sellerRepository.save(seller)
    }
}
