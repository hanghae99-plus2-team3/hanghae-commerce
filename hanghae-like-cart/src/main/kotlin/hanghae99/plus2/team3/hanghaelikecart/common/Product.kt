package hanghae99.plus2.team3.hanghaelikecart.common

class Product(
    val pk: Pk

) {
    @JvmInline
    value class Pk(
        val value: Long
    )
}
