package dip.fakes

class PayPalPaymentProcessor : IPaymentProcessor {
    override fun processPayment() {
        println("Paypal payment processor started")
    }
}