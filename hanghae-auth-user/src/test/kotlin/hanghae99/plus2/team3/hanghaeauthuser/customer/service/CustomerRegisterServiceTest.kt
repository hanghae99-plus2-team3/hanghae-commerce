package hanghae99.plus2.team3.hanghaeauthuser.customer.service


import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.CustomerRegisterRequest
import hanghae99.plus2.team3.hanghaeauthuser.customer.domain.CustomerEntity
import hanghae99.plus2.team3.hanghaeauthuser.customer.exception.AlreadyExistCustomerException
import hanghae99.plus2.team3.hanghaeauthuser.customer.exception.ErrorCode
import hanghae99.plus2.team3.hanghaeauthuser.customer.mock.FakeCustomerRepositoryImpl
import hanghae99.plus2.team3.hanghaeauthuser.customer.repository.CustomerRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CustomerRegisterServiceTest {

    private lateinit var sut: CustomerService
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setUp() {
        prepareTest()
    }

    @Test
    fun `회원이 정상적으로 회원가입에 성공한다`() {
        val customerId = sut.createCustomer(
            CustomerRegisterRequest(
                loginId = "testId3",
                password = "test123"
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
                    password = "test123"
                )
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("로그인ID는 8자 이상 20자 이하로 입력해주세요.")
    }

    @Test
    fun `기 가입된 회원의 경우 회원가입에 실패한다`() {
        assertThatThrownBy {
            sut.createCustomer(
                CustomerRegisterRequest(
                    loginId = "testId11",
                    password = "testPassword123"
                )
            )
        }.isInstanceOf(AlreadyExistCustomerException::class.java)
            .hasMessage(ErrorCode.ALREADY_EXIST_CUSTOMER.message)
    }

    private fun prepareTest() {
        val customerEntities = listOf(
            CustomerEntity(1L, "testId11", "testPassword123", "정찬우"),
            CustomerEntity(2L, "testId22", "testPassword1234", "홍길동")
        )

        customerRepository = FakeCustomerRepositoryImpl(customerEntities)
        sut = CustomerService(customerRepository)
    }
}
