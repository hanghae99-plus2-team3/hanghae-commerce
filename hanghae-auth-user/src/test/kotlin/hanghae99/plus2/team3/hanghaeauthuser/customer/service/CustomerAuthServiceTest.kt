package hanghae99.plus2.team3.hanghaeauthuser.customer.service


import hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request.CustomerLoginRequest
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

class CustomerAuthServiceTest {

    private lateinit var sut: CustomerAuthService
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
                name = "testMan"
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
                    name = "testMan"
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
                    password = "testPassword123",
                    name = "testMan"
                )
            )
        }.isInstanceOf(AlreadyExistCustomerException::class.java)
            .hasMessage(ErrorCode.ALREADY_EXIST_CUSTOMER.message)
    }

    @Test
    fun `사용자가 로그인에 성공한다`() {
        val loginFlag = sut.login(
            CustomerLoginRequest(
                loginId = "testId11",
                password = "testPassword123"
            )
        )

        assertThat(loginFlag).isTrue
    }

    @Test
    fun `아이디, 패스워드 불일치 시 로그인에 실패한다`() {
        assertThatThrownBy {
            sut.login(
                CustomerLoginRequest(
                    loginId = "testId114442",
                    password = "testPassword123"
                )
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    private fun prepareTest() {
        val customerEntities = listOf(
            CustomerEntity(1L, "testId11", "testPassword123", "정찬우"),
            CustomerEntity(2L, "testId22", "testPassword1234", "홍길동")
        )

        customerRepository = FakeCustomerRepositoryImpl(customerEntities)
        sut = CustomerAuthService(customerRepository)
    }
}
