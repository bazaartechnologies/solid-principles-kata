package lsp.fakes

class ZongTopup : Topup() {
    override val taxDetail: Double
        get() = 30.0
    override val serviceName: String
        get() = "Zong"

}