package isp.fakes

class Package: IPackage {
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

    override fun getAmount(): Int {
        return 0
    }
}