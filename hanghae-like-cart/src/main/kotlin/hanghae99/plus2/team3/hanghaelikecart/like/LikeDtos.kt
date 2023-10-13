package hanghae99.plus2.team3.hanghaelikecart.like

import hanghae99.plus2.team3.hanghaelikecart.common.Member
import hanghae99.plus2.team3.hanghaelikecart.common.Product

data class LikeView(
    val pk: Like.Pk,
    val memberPk: Member.Pk,
    val productPk: Product.Pk
) {
    companion object {
        @JvmStatic
        fun from(like: Like): LikeView {
            return LikeView(
                pk = like.pk,
                memberPk = like.memberPk,
                productPk = like.productPk
            )
        }
    }
}
