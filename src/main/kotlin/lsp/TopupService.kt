package lsp

import lsp.fakes.*

class TopupService {

    fun performTopUp(serviceProvider: Topup, amount: Int) {
        serviceProvider.topUp(amount)
    }
}

fun main() {
    val jazzProvider: Topup = JazzTopup()
    val ufoneProvider: Topup = UfoneTopup()
    val zongProvider: Topup = ZongTopup()

    val topUpService = TopupService()
    topUpService.performTopUp(jazzProvider, 100)
    topUpService.performTopUp(ufoneProvider, 100)
    topUpService.performTopUp(zongProvider, 100)
}