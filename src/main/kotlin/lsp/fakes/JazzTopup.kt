package lsp.fakes

open class JazzTopup {

    private val jazzTaxDetail: Double = 20.0
    fun getTaxDetails() {
        println("Jazz tax details: $jazzTaxDetail")
    }
    open fun topUp(amount: Int) {
        val netTopUp = amount - jazzTaxDetail
        println("Jazz top-up with amount: $netTopUp")
    }
}