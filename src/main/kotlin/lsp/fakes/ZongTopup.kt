package lsp.fakes

class ZongTopup(override val taxDetail: Double) : TopUp() {

    private val zongTaxDetail: Double = 20.0

    override fun getTaxDetails(amount: Int) {
        println("Zong tax details: $zongTaxDetail")
    }

    override fun topUp(amount: Int){
        val netTopUp = amount - taxDetail
        println("Ufone top-up with amount: $netTopUp")
    }
}