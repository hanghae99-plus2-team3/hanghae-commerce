package hanghae99.plus2.team3.hanghaeauthuser.customer.service


import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.CustomerRegisterRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.domain.CustomerEntity
import hanghae99.plus2.team3.hanghaeauthuser.customer.mock.FakeCustomerRepositoryImpl
import hanghae99.plus2.team3.hanghaeauthuser.customer.repository.CustomerRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CustomerInfoServiceTest {

    private lateinit var sut: CustomerInfoService
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setUp() {
        prepareTest()
    }

    @Test
    fun `회원이 정상적으로 회원가입에 성공한다`() {
        val customerId = sut.createCustomer(
            CustomerRegisterRequest(
                loginId = "testId33",
                password = "testPassword123",
                name = "testMan",
                email = "test@email.com"
            )
        )

        assertThat(customerId).isNotNull
    }

    @Test
    fun `필수 정보 입력 누락 시 회원가입에 실패한다`() {
        assertThatThrownBy {
            sut.createCustomer(
                CustomerRegisterRequest(
                    loginId = "",
                    password = "test123",
                    name = "testMan",
                    email = "test@email.com"
                )
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("로그인ID는 8자 이상 20자 이하로 입력해주세요.")
    }

    private fun prepareTest() {
        val customerEntities = listOf(
            CustomerEntity(1L, "정찬우", "test@email.com"),
            CustomerEntity(2L, "홍길동", "test@email.com")
        )

        customerRepository = FakeCustomerRepositoryImpl(customerEntities)
        sut = CustomerInfoService(customerRepository)
    }
}
