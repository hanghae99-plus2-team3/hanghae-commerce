package hanghae99.plus2.team3.hanghaeorder.domain.order.config

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.ProductsAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.QueryUserInfoByApi
import hanghae99.plus2.team3.hanghaeorder.domain.order.mock.FakeProductsAccessorImpl
import hanghae99.plus2.team3.hanghaeorder.domain.order.mock.FakeQueryUserInfoByApiImpl
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

/**
 * TestConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/23
 */
@TestConfiguration
class TestConfig {

    @Primary
    @Bean
    fun queryProductsInfo(): ProductsAccessor =
        FakeProductsAccessorImpl(
            mutableListOf(
                ProductsAccessor.ProductInfo(
                    productId = 1L,
                    productName = "상품1",
                    productPrice = 2000L,
                    productStock = 10,
                ),
                ProductsAccessor.ProductInfo(
                    productId = 2L,
                    productName = "상품2",
                    productPrice = 3000L,
                    productStock = 5,
                ),

                )
        )

    @Primary
    @Bean
    fun queryUserInfo(): QueryUserInfoByApi =
        FakeQueryUserInfoByApiImpl(
            listOf(
                QueryUserInfoByApi.UserInfo(
                    userId = 1L,
                    userName = "홍길동",
                    userEmail = "test@gmail.com"
                )
            )
        )
}
