package hanghae99.plus2.team3.hanghaeorder.domain.order.usecase

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.atomic.AtomicLong

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
        val productsInStock = listOf(
            ProductInfo.create(
                productId = 1L,
                productName = "상품1",
                productPrice = 2000L,
                productStock = 10,
            ),
            ProductInfo.create(
                productId = 2L,
                productName = "상품2",
                productPrice = 3000L,
                productStock = 5,
            ),
        )
        sut =
            RegisterOrderUseCaseImpl(
                FakeOrderRepositoryImpl(),
                FakeOrderItemRepositoryImpl(),
                FakeProductServiceImpl(productsInStock)
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
                    productPrice = 1000L,
                ),
                RegisterOrderUseCase.Command.OrderItemCommand(
                    productId = 2L,
                    quantity = 5,
                    productPrice = 5000L,
                ),
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
                    productPrice = 1000L,
                ),
                RegisterOrderUseCase.Command.OrderItemCommand(
                    productId = 2L,
                    quantity = 100,
                    productPrice = 5000L,
                ),
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
                    productPrice = 1000L,
                ),
                RegisterOrderUseCase.Command.OrderItemCommand(
                    productId = 2L,
                    quantity = 100,
                    productPrice = 5000L,
                ),
            )
        )

        assertThatThrownBy { sut.command(command) }
            .isExactlyInstanceOf(ProductNotFoundException::class.java)
            .hasMessage(ErrorCode.PRODUCT_NOT_FOUND.message)
    }

}


class RegisterOrderUseCaseImpl(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val productService: ProductService,
) : RegisterOrderUseCase {

    override fun command(command: RegisterOrderUseCase.Command): String {
        val orderedProductInfo =
            productService.validationRequestOf(
                command.orderItemList.map { it.productId }
            )

        orderedProductsStockValidate(command, orderedProductInfo)

        val savedOrder = orderRepository.save(command.toEntity())

        command.orderItemList.forEach {
            orderItemRepository.save(it.toEntity(orderId = savedOrder.id!!))
        }

        return savedOrder.orderNum
    }

    private fun orderedProductsStockValidate(
        command: RegisterOrderUseCase.Command,
        orderedProductInfo: List<ProductInfo>
    ) {
        command.orderItemList.forEach {
            orderedProductInfo.find { productInfo -> productInfo.productId == it.productId }?.let {
                productInfo ->
                if (productInfo.productStock < it.quantity) {
                    throw ProductStockNotEnoughException()
                }
            }
        }
    }

}

open class OrderException(
    errorCode: ErrorCode
): RuntimeException(errorCode.message)

class ProductStockNotEnoughException():OrderException(ErrorCode.NOT_ENOUGH_STOCK)
class ProductNotFoundException():OrderException(ErrorCode.NOT_ENOUGH_STOCK)

enum class ErrorCode  (
    val message: String,
){
    NOT_ENOUGH_STOCK("주문할 상품의 재고가 부족합니다."),
    PRODUCT_NOT_FOUND("주문할 상품이 존재하지 않습니다.")
    ;
}



interface RegisterOrderUseCase {

    fun command(command: Command): String

    data class Command(
        val userId: Long,
        val receiverName: String,
        val receiverPhone: String,
        val receiverZipCode: String,
        val receiverAddress1: String,
        val receiverAddress2: String,
        val message: String,
        val orderItemList: List<OrderItemCommand>,
    ) {

        fun toEntity(): Order {
            return Order.create(
                userId = userId,
                receiverName = receiverName,
                receiverPhone = receiverPhone,
                receiverZipCode = receiverZipCode,
                receiverAddress1 = receiverAddress1,
                receiverAddress2 = receiverAddress2,
                message = message,
            )
        }

        data class OrderItemCommand(
            val productId: Long,
            val quantity: Int,
            val productPrice: Long,
        ) {
            fun toEntity(orderId: Long): OrderItem {
                return OrderItem.create(
                    productId = productId,
                    quantity = quantity,
                    productPrice = productPrice,
                    orderId = orderId,
                )
            }
        }
    }
}

data class Order private constructor(
    val id: Long? = null,
    val orderNum: String,
    val userId: Long,
    val deliveryInfo: DeliveryInfo,
    val orderStatus: OrderStatus,
) {

    companion object {
        fun create(
            userId: Long,
            receiverName: String,
            receiverPhone: String,
            receiverZipCode: String,
            receiverAddress1: String,
            receiverAddress2: String,
            message: String,
        ): Order {
            return Order(
                id = null,
                orderNum = UUID.randomUUID().toString(),
                userId = userId,
                DeliveryInfo(
                    receiverName = receiverName,
                    receiverPhone = receiverPhone,
                    receiverZipCode = receiverZipCode,
                    receiverAddress1 = receiverAddress1,
                    receiverAddress2 = receiverAddress2,
                    message = message,
                ),
                orderStatus = OrderStatus.ORDERED,
            )
        }

    }

    enum class OrderStatus(
        val description: String
    ) {
        ORDERED("주문 완료");
    }
}

data class DeliveryInfo(
    val receiverName: String,
    val receiverPhone: String,
    val receiverZipCode: String,
    val receiverAddress1: String,
    val receiverAddress2: String,
    val message: String? = null,
) {
    init {
        require(receiverName.isNotBlank()) { "수령인 이름은 필수입니다." }
        require(receiverPhone.isNotBlank()) { "수령인 전화번호는 필수입니다." }
        require(receiverZipCode.isNotBlank()) { "수령인 우편번호는 필수입니다." }
        require(receiverZipCode.length == 5) { "수령인 우편번호는 5자리입니다." }
        require(receiverAddress1.isNotBlank()) { "수령인 주소는 필수입니다." }
        require(receiverAddress2.isNotBlank()) { "수령인 상세주소는 필수입니다." }

    }
}

data class OrderItem private constructor(
    val id: Long? = null,
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val productPrice: Long,
    val deliveryStatus: DeliveryStatus,
) {

    companion object {
        fun create(
            orderId: Long,
            productId: Long,
            quantity: Int,
            productPrice: Long,
        ): OrderItem {

            require(quantity > 0) { "상품 수량은 0보다 커야 합니다." }
            require(productPrice > 0) { "상품 가격은 0보다 커야 합니다." }

            return OrderItem(
                id = null,
                orderId = orderId,
                productId = productId,
                quantity = quantity,
                productPrice = productPrice,
                deliveryStatus = DeliveryStatus.READY,
            )
        }
    }

    enum class DeliveryStatus(
        val description: String
    ) {
        READY("배송 준비중"),
        DELIVERY("배송 중"),
        COMPLETE("배송 완료");
    }
}

interface OrderRepository {
    fun save(order: Order): Order
}

class FakeOrderRepositoryImpl : OrderRepository {
    private val autoGeneratedId = AtomicLong(0)
    private val orders = Collections.synchronizedList(mutableListOf<OrderEntity>())

    override fun save(order: Order): Order {
        val entity = OrderEntity(
            id = autoGeneratedId.incrementAndGet(),
            orderNum = order.orderNum,
            userId = order.userId,
            deliveryInfo = order.deliveryInfo,
            orderStatus = order.orderStatus,
        )
        orders.add(entity)
        return order.copy(id = entity.id)
    }
}


data class OrderEntity(
    val id: Long? = null,
    val orderNum: String,
    val userId: Long,
    val deliveryInfo: DeliveryInfo,
    val orderStatus: Order.OrderStatus,
)

data class OrderItemEntity(
    val id: Long? = null,
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val productPrice: Long,
    val deliveryStatus: OrderItem.DeliveryStatus,
)

data class DeliveryInfoEntity(
    val receiverName: String,
    val receiverPhone: String,
    val receiverZipCode: String,
    val receiverAddress1: String,
    val receiverAddress2: String,
    val message: String? = null,
)

interface OrderItemRepository {
    fun save(orderItem: OrderItem): OrderItem
}

class FakeOrderItemRepositoryImpl : OrderItemRepository {
    private val autoGeneratedId = AtomicLong(0)
    private val orderItems = Collections.synchronizedList(mutableListOf<OrderItemEntity>())

    override fun save(orderItem: OrderItem): OrderItem {
        val entity = OrderItemEntity(
            id = autoGeneratedId.incrementAndGet(),
            orderId = orderItem.orderId,
            productId = orderItem.productId,
            quantity = orderItem.quantity,
            productPrice = orderItem.productPrice,
            deliveryStatus = orderItem.deliveryStatus,
        )
        orderItems.add(entity)
        return orderItem.copy(id = entity.id)
    }
}


interface ProductService {
    fun validationRequestOf(productIds: List<Long>): List<ProductInfo>
}

class FakeProductServiceImpl(
    private val productInfo: List<ProductInfo>
) : ProductService {

    override fun validationRequestOf(productIds: List<Long>): List<ProductInfo> {
        return productInfo.filter { productIds.contains(it.productId) }
    }
}

data class ProductInfo private constructor(
    val productId: Long,
    val productName: String,
    val productPrice: Long,
    val productStock: Int,
) {

    companion object {
        fun create(
            productId: Long,
            productName: String,
            productPrice: Long,
            productStock: Int,
        ): ProductInfo {

            return ProductInfo(
                productId = productId,
                productName = productName,
                productPrice = productPrice,
                productStock = productStock
            )
        }
    }
}
