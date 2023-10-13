package hanghae99.plus2.team3.hanghaelikecart.like

import hanghae99.plus2.team3.hanghaelikecart.common.AuthTokenInfo
import hanghae99.plus2.team3.hanghaelikecart.common.Product
import hanghae99.plus2.team3.hanghaelikecart.common.RequestAuth
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/likes")
class LikeController(
    private val likeService: LikeService
) {

    @PostMapping
    fun registerLike(
        @RequestAuth tokenInfo: AuthTokenInfo
    ) {
        likeService.registerLike(memberPk = tokenInfo.getMemberPk(), productPk = Product.Pk(1L))
    }

    @GetMapping
    fun getLikeList(
        @RequestAuth tokenInfo: AuthTokenInfo,
        @PageableDefault page: Pageable
    ): Page<Like> {
        return likeService.getLikeList(memberPk = tokenInfo.getMemberPk(), page = page)
    }

    @DeleteMapping("/{likePk}")
    fun deleteLike(
        @RequestAuth tokenInfo: AuthTokenInfo,
        @PathVariable likePk: Like.Pk
    ) {
        likeService.deleteLike(likePk = likePk)
    }
}
