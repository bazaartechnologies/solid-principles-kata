package lsp.fakes

class JazzTopup() : Topup() {
    override val taxDetail: Double
        get() = 10.0
    override val serviceName: String
        get() = "Jazz"

}