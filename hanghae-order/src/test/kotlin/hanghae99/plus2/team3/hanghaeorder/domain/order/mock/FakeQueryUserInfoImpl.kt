package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.order.QueryUserInfo
import hanghae99.plus2.team3.hanghaeorder.domain.order.QueryUserInfo.*

class FakeQueryUserInfoImpl(
    private val userInfos: List<UserInfo>
) : QueryUserInfo {

    override fun query(userId: Long): UserInfo? {
        return userInfos.findLast { it.userId == userId }
    }
}
