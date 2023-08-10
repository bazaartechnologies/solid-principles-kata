package isp.fakes

interface Airtime {
    fun getTelcoId(): String
    fun getUserName(): String
    fun getPhoneNumber(): String
    fun getAmount(): Int
}