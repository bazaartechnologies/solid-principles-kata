package dip.fakes

class PayPalPaymentProcessor: PaymentProcessor {
    override fun processPayment() {
        println("Paypal payment processor started")
    }
}