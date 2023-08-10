package dip

import dip.fakes.BitcoinPaymentProcessor
import dip.fakes.MasterCreditCardPaymentProcessor
import dip.fakes.PayPalPaymentProcessor
import dip.fakes.PaymentProcessor

class ShoppingCartService {

    fun checkout(paymentOption: String) {
        paymentProcessorProvider(paymentOption)
    }
}

fun main() {
    val shoppingCartService = ShoppingCartService()
    shoppingCartService.checkout("payPal")

}

fun paymentProcessorProvider(paymentOption: String): PaymentProcessor{
    return when (paymentOption) {
        "creditCard" -> MasterCreditCardPaymentProcessor()
        "payPal" -> PayPalPaymentProcessor()
        "bitcoin" -> BitcoinPaymentProcessor()
        else -> throw IllegalArgumentException("Invalid payment option")
    }
}