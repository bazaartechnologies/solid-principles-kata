package lsp.fakes

class JazzTopup : TopupRequestDto() {

    override fun getTaxPercentage(): Int {
        return 20
    }
    override fun retryTopup() {
        TODO("Not yet implemented")
    }
}