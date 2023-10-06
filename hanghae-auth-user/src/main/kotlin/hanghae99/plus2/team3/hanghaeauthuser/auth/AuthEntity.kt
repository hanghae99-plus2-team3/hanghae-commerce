package hanghae99.plus2.team3.hanghaeauthuser.auth

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pk: Long,
    val createdTime: LocalDateTime
)

@Entity
class AuthMemberEntity(
    pk: Long,
    val loginId: String,
    val pw: String,
    createdTime: LocalDateTime
) : BaseEntity(pk = pk, createdTime = createdTime)

@Entity
class AuthTokenEntity(
    pk: Long,
    val token: String,
    val memberPk: Long,
    createdTime: LocalDateTime
) : BaseEntity(pk = pk, createdTime = createdTime)
