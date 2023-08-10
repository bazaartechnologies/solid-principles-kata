package dip.fakes

class BitcoinPaymentProcessor : IPaymentProcessor {
    override fun processPayment() {
        println("Bitcoin Credit Card payment processor started")
    }
}