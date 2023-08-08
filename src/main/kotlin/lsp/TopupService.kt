package lsp

import lsp.fakes.*

class TopupService {

    fun performTopUp(serviceProvider: JazzTopup, amount: Int) {
        serviceProvider.topUp(amount)
    }
}

fun main() {
    val jazzProvider = JazzTopup()
    val ufoneProvider: JazzTopup = UfoneTopup()
    val zongProvider: JazzTopup = ZongTopup()

    val topUpService = TopupService()
    topUpService.performTopUp(jazzProvider, 100)
    topUpService.performTopUp(ufoneProvider, 100)
    topUpService.performTopUp(zongProvider, 100)
}