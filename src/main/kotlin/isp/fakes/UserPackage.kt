package isp.fakes

class UserPackage: TelcoUser, Package {
    override fun getTelcoId(): String {
        return "telco-id-2"
    }

    override fun getUserName(): String {
        return "user-id-2"
    }

    override fun getPhoneNumber(): String {
        return "03331234567"
    }

    override fun getPackageId(): String {
        return "package-1"
    }
}