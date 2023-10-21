package hanghae99.plus2.team3.hanghaeauthuser.customer.controller.request

data class SearchProductRequest(
    val searchText: String
) {
    init {
        require(searchText.isNotBlank()) {
            "검색어를 입력해주세요."
        }
    }
}
