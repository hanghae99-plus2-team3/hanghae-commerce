package hanghae99.plus2.team3.hanghaelikecart.like

import hanghae99.plus2.team3.hanghaelikecart.common.AuthFeignClient
import hanghae99.plus2.team3.hanghaelikecart.common.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/likes")
class LikeController(
    private val authFeignClient: AuthFeignClient,
    private val likeService: LikeService
) {

    @PostMapping
    fun registerLike(
        @RequestHeader("authorization") authorization: String
    ) {
        val tokenInfo = authFeignClient.getTokenInfo(authorization = authorization)
        likeService.registerLike(memberPk = tokenInfo.getMemberPk(), productPk = Product.Pk(1L))
    }

    @GetMapping
    fun getLikeList(
        @RequestHeader("authorization") authorization: String,
        @PageableDefault page: Pageable
    ): Page<Like> {
        val tokenInfo = authFeignClient.getTokenInfo(authorization = authorization)
        return likeService.getLikeList(memberPk = tokenInfo.getMemberPk(), page = page)
    }

    @DeleteMapping("/{likePk}")
    fun deleteLike(
        @RequestHeader("authorization") authorization: String,
        @PathVariable likePk: Like.Pk
    ) {
        val tokenInfo = authFeignClient.getTokenInfo(authorization = authorization)
        likeService.deleteLike(likePk = likePk)
    }
}
