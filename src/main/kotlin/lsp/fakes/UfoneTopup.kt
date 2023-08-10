package lsp.fakes

class UfoneTopup : JazzTopup() {

    private val ufoneTaxDetail: Double = 20.0
    fun getTaxDetails(amount: Int) {
        println("Ufone tax details: $ufoneTaxDetail")
    }
}