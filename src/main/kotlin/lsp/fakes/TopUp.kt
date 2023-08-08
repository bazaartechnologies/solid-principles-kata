package lsp.fakes

abstract class TopUp {

    abstract val taxDetail: Double

    abstract fun getTaxDetails(amount: Int)

    abstract fun topUp(amount: Int)
}