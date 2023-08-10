package dip

import dip.fakes.BitcoinPaymentProcessor
import dip.fakes.IPaymentProcessor
import dip.fakes.MasterCreditCardPaymentProcessor
import dip.fakes.PayPalPaymentProcessor

class ShoppingCartService {

    private fun getPaymentProcessor(paymentOption: String): IPaymentProcessor {
        return when (paymentOption) {
            "creditCard" -> MasterCreditCardPaymentProcessor()
            "payPal" -> PayPalPaymentProcessor()
            "bitcoin" -> BitcoinPaymentProcessor()
            else -> throw IllegalArgumentException("Invalid payment option")
        }
    }

    fun checkout(paymentOption: String) {
        getPaymentProcessor(paymentOption).processPayment()
    }

}

fun main() {
    val shoppingCartService = ShoppingCartService()
    shoppingCartService.checkout("payPal")

}