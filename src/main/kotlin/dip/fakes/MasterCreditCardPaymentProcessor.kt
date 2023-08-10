package dip.fakes

class MasterCreditCardPaymentProcessor: PaymentProcessor {
    override fun processPayment() {
        println("Master Credit Card payment processor started")
    }
}