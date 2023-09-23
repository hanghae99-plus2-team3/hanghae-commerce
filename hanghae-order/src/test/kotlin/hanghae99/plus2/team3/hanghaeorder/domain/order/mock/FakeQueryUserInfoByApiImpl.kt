package hanghae99.plus2.team3.hanghaeorder.domain.order.mock

import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.QueryUserInfoByApi
import hanghae99.plus2.team3.hanghaeorder.domain.order.infrastructure.QueryUserInfoByApi.*

class FakeQueryUserInfoByApiImpl(
    private val userInfos: List<UserInfo>
) : QueryUserInfoByApi {

    override fun query(userId: Long): UserInfo? {
        return userInfos.findLast { it.userId == userId }
    }
}
