package hanghae99.plus2.team3.commerce.jaehyun.seller.small

import hanghae99.plus2.team3.commerce.jaehyun.common.exception.ErrorCode
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.Seller
import hanghae99.plus2.team3.commerce.jaehyun.seller.mock.FakeSellerRepositoryImpl
import hanghae99.plus2.team3.commerce.jaehyun.seller.mock.SellerMemoryRepository
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.usecase.RegisterSellerUseCase
import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.usecase.RegisterSellerUseCaseImpl
import hanghae99.plus2.team3.commerce.jaehyun.seller.exception.SellerNotFoundException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * RegisterSellerUseCaseTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/18
 */
class RegisterSellerUseCaseTest {
    private lateinit var registerSellerUseCase: RegisterSellerUseCase

    @BeforeEach
    fun setUp() {
        val fakeSellerRepository = FakeSellerRepositoryImpl(
            SellerMemoryRepository()
        )
        registerSellerUseCase = RegisterSellerUseCaseImpl(
            fakeSellerRepository
        )
        fakeSellerRepository.save(Seller(id = 1L, name = "판매자1"))
    }

    @Test
    fun `정상적으로 판매자 등록을 요청하면 기대하는 응답을 반환한다`() {
        val command = RegisterSellerUseCase.Command(
            name = "판매자2",
        )

        val savedSeller = registerSellerUseCase.command(command)

        assertThat(savedSeller.id).isNotNull
        assertThat(savedSeller.name).isEqualTo(command.name)
    }

    @Test
    fun `이미 등록된 판매자명으로 등록을 요청하면 기대하는 응답(exception)을 반환한다`() {
        val command = RegisterSellerUseCase.Command(
            name = "판매자1",
        )

        Assertions.assertThatThrownBy {  registerSellerUseCase.command(command) }
            .isExactlyInstanceOf(SellerNameDuplicatedException::class.java)
            .hasMessage(ErrorCode.SELLER_NOT_FOUND.message)
    }
}

