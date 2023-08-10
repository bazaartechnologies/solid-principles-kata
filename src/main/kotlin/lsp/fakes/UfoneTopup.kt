package lsp.fakes

class UfoneTopup : Topup() {
    override val taxDetail: Double
        get() = 20.0
    override val serviceName: String
        get() = "Ufone"
}