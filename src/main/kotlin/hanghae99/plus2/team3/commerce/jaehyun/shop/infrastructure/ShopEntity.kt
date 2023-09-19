package hanghae99.plus2.team3.commerce.jaehyun.shop.infrastructure

import hanghae99.plus2.team3.commerce.jaehyun.seller.infrastructure.SellerEntity
import hanghae99.plus2.team3.commerce.jaehyun.shop.domain.Shop
import javax.persistence.*

@Entity
class ShopEntity(
    id: Long,
    name: String,
    seller: SellerEntity,
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id ?: 0L

    var name: String = name
        private set

    @OneToOne(fetch = FetchType.LAZY)
    var seller: SellerEntity = seller
        private set

    fun toDomain(): Shop {
        return Shop(
            id = id,
            name = name,
            seller = seller.toDomain(),
        )
    }
}
