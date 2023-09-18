package hanghae99.plus2.team3.commerce.seller

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
        registerSellerUseCase = RegisterSellerUseCaseImpl()
    }

    @Test
    fun `정상적으로 판매자 등록을 요청하면 기대하는 응답을 반환한다`(){
        val commmand = RegisterSellerUseCase.Command(
            name = "판매자1",
            email = "test@gmail.com",
        )

        val savedSeller = registerSellerUseCase.command(commmand)
    }
}
