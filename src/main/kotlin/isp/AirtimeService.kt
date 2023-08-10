package isp

import isp.fakes.AirtimeType
import isp.fakes.EasyLoadImpl
import isp.fakes.PackageImpl

class AirtimeService {

    fun getAirtimeDto(type: AirtimeType) {
        when (type) {
            AirtimeType.EASYLOAD -> EasyLoadImpl()
            AirtimeType.PACKAGE -> PackageImpl()
        }
    }
}