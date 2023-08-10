package isp.fakes

interface EasyLoad: Airtime {
    fun getEasyloadId(): String
    fun getAmount(): Int
}