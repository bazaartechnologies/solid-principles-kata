package dip.fakes

class MasterCreditCardPaymentProcessor : IPaymentProcessor{
    override fun processPayment() {
        println("Master Credit Card payment processor started")
    }
}