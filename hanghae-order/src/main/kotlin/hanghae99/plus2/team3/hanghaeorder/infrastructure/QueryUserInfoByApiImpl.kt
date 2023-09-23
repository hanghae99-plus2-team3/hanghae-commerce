package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.QueryUserInfoByApi
import org.springframework.stereotype.Component

/**
 * QueryUserInfoImpl
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/23
 */
@Component
class QueryUserInfoByApiImpl : QueryUserInfoByApi {
    override fun query(userId: Long): QueryUserInfoByApi.UserInfo? {
        TODO("Not yet implemented")
    }
}
