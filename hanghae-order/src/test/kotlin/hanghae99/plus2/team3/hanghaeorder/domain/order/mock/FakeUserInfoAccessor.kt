package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.UserInfoAccessor.UserInfo

class FakeUserInfoAccessor(
    private val userInfos: List<UserInfo>
) : UserInfoAccessor {
    override fun query(token: String): UserInfo? {
        return userInfos.findLast { it.name == token }
    }
}
