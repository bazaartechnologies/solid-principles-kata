package dip

import dip.fakes.BitcoinPaymentProcessor
import dip.fakes.CreditCardPaymentProcessor
import dip.fakes.PayPalPaymentProcessor

class ShoppingCartService {

    private val creditCardPaymentProcessor = CreditCardPaymentProcessor()
    private val payPalPaymentProcessor = PayPalPaymentProcessor()
    private val bitcoinPaymentProcessor = BitcoinPaymentProcessor()

    fun checkout(paymentOption: String) {
        when (paymentOption) {
            "creditCard" -> creditCardPaymentProcessor.processPayment()
            "payPal" -> payPalPaymentProcessor.processPayment()
            "bitcoin" -> bitcoinPaymentProcessor.processPayment()
            else -> throw IllegalArgumentException("Invalid payment option")
        }
    }

}