package dip

import dip.fakes.BitcoinPaymentProcessor
import dip.fakes.MasterCreditCardPaymentProcessor
import dip.fakes.PayPalPaymentProcessor

class ShoppingCartService {

    private val masterCreditCardPaymentProcessor = MasterCreditCardPaymentProcessor()
    private val payPalPaymentProcessor = PayPalPaymentProcessor()
    private val bitcoinPaymentProcessor = BitcoinPaymentProcessor()

    fun checkout(paymentOption: String) {
        when (paymentOption) {
            "creditCard" -> masterCreditCardPaymentProcessor.processPayment()
            "payPal" -> payPalPaymentProcessor.processPayment()
            "bitcoin" -> bitcoinPaymentProcessor.processPayment()
            else -> throw IllegalArgumentException("Invalid payment option")
        }
    }

}

fun main() {
    val shoppingCartService = ShoppingCartService()
    shoppingCartService.checkout("payPal")

}