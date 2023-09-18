package hanghae99.plus2.team3.commerce.seller

import hanghae99.plus2.team3.commerce.seller.domain.Seller
import hanghae99.plus2.team3.commerce.seller.domain.SellerRepository
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
        registerSellerUseCase = RegisterSellerUseCaseImpl(
            FakeSellerRepositoryImpl(
                SellerMemoryRepository()
            )
        )
    }

    @Test
    fun `정상적으로 판매자 등록을 요청하면 기대하는 응답을 반환한다`() {
        val commmand = RegisterSellerUseCase.Command(
            name = "판매자1",
        )

        val savedSeller = registerSellerUseCase.command(commmand)

        assertThat(savedSeller.id).isNotNull
        assertThat(savedSeller.name).isEqualTo(commmand.name)
    }
}

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

data class SellerEntity(
    val id: Long,
    val name: String,
)

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
