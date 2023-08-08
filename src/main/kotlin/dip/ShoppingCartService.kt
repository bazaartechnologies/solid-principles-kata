package dip

import dip.fakes.BitcoinPaymentProcessor
import dip.fakes.CreditCardPaymentProcessor
import dip.fakes.PayPalPaymentProcessor

interface PaymentProcessor {
    fun processPayment()
}

class ShoppingCartService {

    fun checkout(paymentOption: String) {
        val paymentProcessor = PaymentProcessorProvider.provide(paymentOption)
        paymentProcessor.processPayment()
    }

}

object PaymentProcessorProvider {

    fun provide(paymentOption: String): PaymentProcessor {
        return when (paymentOption) {
            "creditCard" -> CreditCardPaymentProcessor()
            "payPal" -> PayPalPaymentProcessor()
            "bitcoin" -> BitcoinPaymentProcessor()
            else -> throw IllegalArgumentException("Invalid payment option")
        }

    }
}

