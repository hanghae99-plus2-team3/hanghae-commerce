package hanghae99.plus2.team3.hanghaeproduct

data class CreateProductRequest(
    val productId: String,
    val name: String,
    val price: Int,
    val stock: Long,
)
