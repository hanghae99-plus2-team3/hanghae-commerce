package hanghae99.plus2.team3.commerce.jaehyun.product

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.atomic.AtomicLong

/**
 * ProductServiceTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/19
 */
class AddProductUseCaseTest {

    private lateinit var addProductUseCase: AddProductUseCase

    @Test
    fun `정상적으로 상품 등록을 요청하면 기대하는 응답을 반환한다`() {
        val command: AddProductUseCase.Command = AddProductUseCase.Command(
            name = "상품1",
            price = 1000,
            quantity = 10,
            sellerId = 1L,
            shopId = 1L,
            category = Category.CLOTHES,
        )

        val savedProduct = addProductUseCase.command(command)

        assertThat(savedProduct.id).isNotNull
        assertThat(savedProduct.name).isEqualTo(command.name)
        assertThat(savedProduct.price).isEqualTo(command.price)
        assertThat(savedProduct.quantity).isEqualTo(command.quantity)
        assertThat(savedProduct.sellerId).isEqualTo(command.sellerId)
        assertThat(savedProduct.category).isEqualTo(command.category)

    }
}

interface AddProductUseCase {
    fun command(command: Command) : Product

    data class Command(
        val name: String,
        val price: Int,
        val quantity: Int,
        val sellerId: Long,
        val shopId: Long,
        val category: Category,
    )
}

data class Product(
    val id: Long = 0,
    val name: String,
    val price: Int,
    val quantity: Int,
    val sellerId: Long,
    val category: Category,
) {
    init {
        require(name.isNotBlank()) { "상품 이름은 필수입니다." }
        require(price >= 0) { "잘못된 상품 가격입니다." }
        require(quantity >= 0) { "상품 수량이 0개 미만입니다." }
        require(sellerId >= 0) { "잘못된 판매자 ID 입니다." }
    }
}

enum class Category {
    CLOTHES,
}

