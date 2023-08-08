package isp.fakes

interface Airtime {
    fun getTelcoId(): String
    fun getUserName(): String
    fun getPhoneNumber(): String
    fun getPackageId(): String
    fun getEasyloadId(): String
    fun getAmount(): Int
}