package hanghae99.plus2.team3.hanghaeauthuser.customer.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class CustomerEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pk: Long,
    val loginId: String,
    val password: String,
    val name: String
)
