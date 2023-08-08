package isp.fakes

class EasyLoad: Airtime {
    override fun getTelcoId(): String {
        return "telco-id-1"
    }

    override fun getUserName(): String {
        return "user-id-1"
    }

    override fun getPhoneNumber(): String {
        return "03001234567"
    }

    override fun getPackageId(): String {
        return ""
    }

    override fun getEasyloadId(): String {
        return "easyload-1"
    }

    override fun getAmount(): Int {
        return 2000
    }
}