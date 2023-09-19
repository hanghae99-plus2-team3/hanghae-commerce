package hanghae99.plus2.team3.commerce.jaehyun.seller.infrastructure

import hanghae99.plus2.team3.commerce.jaehyun.seller.domain.Seller
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class SellerEntity(
    id: Long?,
    name: String,
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id ?: 0L

    var name: String = name
        private set

    constructor(seller: Seller) : this(
        id = seller.id,
        name = seller.name,
    )

    fun toDomain(): Seller {
        return Seller(
            id = id,
            name = name,
        )
    }
}
