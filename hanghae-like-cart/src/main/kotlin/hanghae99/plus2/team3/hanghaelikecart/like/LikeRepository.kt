package hanghae99.plus2.team3.hanghaelikecart.like

import hanghae99.plus2.team3.hanghaelikecart.common.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class LikeRepository(
    private val likeJpaRepository: LikeJpaRepository
) {

    fun save(like: Like): Like {
        return likeJpaRepository.save(like)
    }

    fun getPage(memberPk: Member.Pk, page: Pageable): Page<Like> {
        return likeJpaRepository.findAllByMemberPk(memberPk = memberPk.value, page = page)
    }

    fun delete(likePk: Like.Pk) {
        likeJpaRepository.deleteByPk(likePk.value)
    }
}

interface LikeJpaRepository : JpaRepository<Like, Long> {
    fun findAllByMemberPk(memberPk: Long, page: Pageable): Page<Like>
    fun deleteByPk(pk: Long)
}
