package isp

import isp.fakes.AirtimeType
import isp.fakes.EasyLoad
import isp.fakes.Package

class AirtimeService {

    fun getAirtimeDto(type: AirtimeType) {
        when (type) {
            AirtimeType.EASYLOAD -> EasyLoad()
            AirtimeType.PACKAGE -> Package()
        }
    }
}