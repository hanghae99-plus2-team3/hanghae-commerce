package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.QueryUserInfo
import org.springframework.stereotype.Component

/**
 * QueryUserInfoImpl
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/23
 */
@Component
class QueryUserInfoImpl : QueryUserInfo {
    override fun query(userId: Long): QueryUserInfo.UserInfo? {
        TODO("Not yet implemented")
    }
}
