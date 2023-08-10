package lsp.fakes

open class JazzTopup {

    private val taxDetail: Double = 20.0
    fun getTaxDetails() {
        println("Jazz tax details: $taxDetail")
    }
    open fun topUp(amount: Int) {
        val netTopUp = amount - taxDetail
        println("Jazz top-up with amount: $netTopUp")
    }
}