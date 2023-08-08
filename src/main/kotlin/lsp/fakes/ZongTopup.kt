package lsp.fakes

class ZongTopup : JazzTopup() {

    private val zongTaxDetail: Double = 20.0
    fun getZongTaxDetails(amount: Int) {
        println("Zong tax details: $zongTaxDetail")
    }
}