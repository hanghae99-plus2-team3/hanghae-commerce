package hanghae99.plus2.team3.hanghaelikecart.like

import hanghae99.plus2.team3.hanghaelikecart.common.Member
import hanghae99.plus2.team3.hanghaelikecart.common.Product
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "like_table")
@Entity
class Like(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pk: Pk,
    val memberPk: Member.Pk,
    val productPk: Product.Pk
) {
    @JvmInline
    value class Pk(
        val value: Long
    )
}
