package lsp.fakes

class ZongTopup : JazzTopup() {

    private val taxDetail: Double = 15.0
    fun getTaxDetails(amount: Int) {
        println("Zong tax details: $taxDetail")
    }
}