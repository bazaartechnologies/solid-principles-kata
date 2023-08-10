package lsp.fakes

abstract class Topup {

    abstract val taxDetail: Double
    abstract val serviceName: String

    fun getTaxDetails() {
        println("$serviceName tax details: $taxDetail")
    }
    open fun topUp(amount: Int) {
        val netTopUp = amount - taxDetail
        println("$serviceName top-up with amount: $netTopUp")
    }
}