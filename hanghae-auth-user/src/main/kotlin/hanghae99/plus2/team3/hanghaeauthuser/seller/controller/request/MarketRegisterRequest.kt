package hanghae99.plus2.team3.hanghaeauthuser.seller.controller.request

data class MarketRegisterRequest(
    val name: String,
    val category: String
) {
    init {
        require(name.length in 2..20) {
            "상점명은 2자 이상 20자 이하로 입력해주세요."
        }
        require(category.isNotBlank()) {
            "카테고리를 입력해주세요."
        }
    }
}
