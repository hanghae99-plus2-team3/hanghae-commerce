package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor.*

class FakeUserInfoAccessor(
    private val userInfos: List<UserInfo>
) : UserInfoAccessor {

    override fun query(userId: Long): UserInfo? {
        return userInfos.findLast { it.userId == userId }
    }
}
