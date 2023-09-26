package hanghae99.plus2.team3.hanghaeorder.infrastructure

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor
import org.springframework.stereotype.Component

/**
 * QueryUserInfoImpl
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/23
 */
@Component
class UserInfoAccessorImpl : UserInfoAccessor {
    override fun query(userId: Long): UserInfoAccessor.UserInfo? {
        TODO("Not yet implemented")
    }
}
