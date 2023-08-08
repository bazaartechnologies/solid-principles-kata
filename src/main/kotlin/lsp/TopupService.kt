package lsp

import lsp.fakes.*

class TopupService {

    fun performTopUp(serviceProvider: TopUp, amount: Int) {
        serviceProvider.topUp(amount)
    }
}

fun main() {
    val jazzProvider = JazzTopup(20.0)
    val ufoneProvider = UfoneTopup(20.0)
    val zongProvider = ZongTopup(20.0)

    val topUpService = TopupService()
    topUpService.performTopUp(jazzProvider, 100)
    topUpService.performTopUp(ufoneProvider, 100)
    topUpService.performTopUp(zongProvider, 100)
}