package lsp.fakes

open class JazzTopup(override val taxDetail: Double): TopUp() {


    override fun getTaxDetails(amount: Int) {
        println("Jazz tax details: $taxDetail")
    }

    override fun topUp(amount: Int) {
        val netTopUp = amount - taxDetail
        println("Jazz top-up with amount: $netTopUp")
    }
}
