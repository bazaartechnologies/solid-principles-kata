package dip.fakes

class BitcoinPaymentProcessor: PaymentProcessor {
    override fun processPayment() {
        println("Bitcoin Credit Card payment processor started")
    }
}