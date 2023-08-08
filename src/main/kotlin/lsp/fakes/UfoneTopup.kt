package lsp.fakes

class UfoneTopup(override val taxDetail: Double) : TopUp() {

    private val ufoneTaxDetail: Double = 20.0
    override fun getTaxDetails(amount: Int) {
        println("Ufone tax details: $ufoneTaxDetail")
    }

    override fun topUp(amount: Int){
        val netTopUp = amount - taxDetail
        println("Ufone top-up with amount: $netTopUp")
    }

}