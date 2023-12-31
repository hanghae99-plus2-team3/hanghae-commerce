package hanghae99.plus2.team3.hanghaeorder.domain.order.small

import hanghae99.plus2.team3.hanghaeorder.common.exception.ErrorCode
import hanghae99.plus2.team3.hanghaeorder.common.exception.ProductNotFoundException
import hanghae99.plus2.team3.hanghaeorder.common.exception.ProductStockNotEnoughException
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.mock.FakeOrderItemRepositoryImpl
import hanghae99.plus2.team3.hanghaeorder.domain.order.mock.FakeOrderRepositoryImpl
import hanghae99.plus2.team3.hanghaeorder.domain.order.mock.FakeProductsAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.service.OrderService
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.RegisterOrderUseCase
import hanghae99.plus2.team3.hanghaeorder.domain.order.usecase.impl.RegisterOrderUseCaseImpl
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * RegisterOrderUseCaseTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/21
 */
class RegisterOrderUseCaseTest {
    private lateinit var sut: RegisterOrderUseCase

    @BeforeEach
    fun setUp() {
        val productsInStock = mutableListOf(
            ProductsAccessor.ProductInfo(productId = 1L, productName = "상품1", productPrice = 2000L, productStock = 10),
            ProductsAccessor.ProductInfo(productId = 2L, productName = "상품2", productPrice = 3000L, productStock = 5)
        )

        sut =
            RegisterOrderUseCaseImpl(
                OrderService(
                    FakeOrderRepositoryImpl(listOf()),
                    FakeOrderItemRepositoryImpl(listOf()),
                    FakeProductsAccessor(productsInStock)
                )
            )
    }

    @Test
    fun `정상적으로 상품을 주문하면 기대하는 응답(성공)을 반환한다`() {
        val command = RegisterOrderUseCase.Command(
            userId = 1L,
            receiverName = "홍길동",
            receiverPhone = "010-1234-5678",
            receiverZipCode = "12345",
            receiverAddress1 = "서울시 강남구",
            receiverAddress2 = "역삼동 123-456",
            message = "부재시 경비실에 맡겨주세요",
            orderItemList = listOf(
                RegisterOrderUseCase.Command.OrderItemCommand(
                    productId = 1L,
                    quantity = 2,
                    productPrice = 1000L
                ),
                RegisterOrderUseCase.Command.OrderItemCommand(
                    productId = 2L,
                    quantity = 5,
                    productPrice = 5000L
                )
            )
        )
        val orderNum = sut.command(command)

        assertThat(orderNum).isNotEmpty()
    }

    @Test
    fun `상품 주문시 상품의 재고가 부족하면 기대하는 응답(실패)을 반환한다`() {
        val command = RegisterOrderUseCase.Command(
            userId = 1L,
            receiverName = "홍길동",
            receiverPhone = "010-1234-5678",
            receiverZipCode = "12345",
            receiverAddress1 = "서울시 강남구",
            receiverAddress2 = "역삼동 123-456",
            message = "부재시 경비실에 맡겨주세요",
            orderItemList = listOf(
                RegisterOrderUseCase.Command.OrderItemCommand(
                    productId = 1L,
                    quantity = 2,
                    productPrice = 1000L
                ),
                RegisterOrderUseCase.Command.OrderItemCommand(
                    productId = 2L,
                    quantity = 100,
                    productPrice = 5000L
                )
            )
        )

        assertThatThrownBy { sut.command(command) }
            .isExactlyInstanceOf(ProductStockNotEnoughException::class.java)
            .hasMessage(ErrorCode.NOT_ENOUGH_STOCK.message)
    }

    @Test
    fun ` 존재 하지 않는 상품을 주문하면 기대하는 응답(실패)을 반환한다`() {
        val command = RegisterOrderUseCase.Command(
            userId = 1L,
            receiverName = "홍길동",
            receiverPhone = "010-1234-5678",
            receiverZipCode = "12345",
            receiverAddress1 = "서울시 강남구",
            receiverAddress2 = "역삼동 123-456",
            message = "부재시 경비실에 맡겨주세요",
            orderItemList = listOf(
                RegisterOrderUseCase.Command.OrderItemCommand(
                    productId = 999L,
                    quantity = 2,
                    productPrice = 1000L
                )
            )
        )

        assertThatThrownBy { sut.command(command) }
            .isExactlyInstanceOf(ProductNotFoundException::class.java)
            .hasMessage(ErrorCode.PRODUCT_NOT_FOUND.message)
    }
}
