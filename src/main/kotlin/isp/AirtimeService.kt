package isp

import isp.fakes.AirtimeType
import isp.fakes.UserEasyLoad
import isp.fakes.UserPackage

class AirtimeService {

    fun getAirtimeDto(type: AirtimeType) {
        when (type) {
            AirtimeType.EASYLOAD -> UserEasyLoad()
            AirtimeType.PACKAGE -> UserPackage()
        }
    }
}