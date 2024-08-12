package tadoclient.apis.verify

import tadoclient.models.MobileDevice

fun verifyMobileDevice(mobileDevice: MobileDevice, context:String, parentName:String = "MobileDevice") {
    verifyAny(mobileDevice, context, parentName)
    verifyAny(mobileDevice.deviceMetadata!!, context, "$parentName.deviceMetadata")
    verifyAny(mobileDevice.settings!!, context, "$parentName.settings")
}