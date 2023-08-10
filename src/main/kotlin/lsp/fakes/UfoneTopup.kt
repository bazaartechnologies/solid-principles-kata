package lsp.fakes

class UfoneTopup : JazzTopup() {

    private val taxDetail: Double = 10.0
    fun getTaxDetails(amount: Int) {
        println("Ufone tax details: $taxDetail")
    }
}