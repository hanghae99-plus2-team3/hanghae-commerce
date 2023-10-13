package hanghae99.plus2.team3.hanghaelikecart.like

import hanghae99.plus2.team3.hanghaelikecart.common.Member
import hanghae99.plus2.team3.hanghaelikecart.common.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LikeService(
    private val likeRepository: LikeRepository
) {

    fun registerLike(
        memberPk: Member.Pk,
        productPk: Product.Pk
    ) {
        val like = Like(
            pk = Like.Pk(0),
            memberPk = memberPk,
            productPk = productPk
        )
        likeRepository.save(like)
    }

    fun getLikeList(memberPk: Member.Pk, page: Pageable): Page<Like> {
        return likeRepository.getPage(memberPk, page)
    }

    fun deleteLike(likePk: Like.Pk) {
        likeRepository.delete(likePk = likePk)
    }
}
