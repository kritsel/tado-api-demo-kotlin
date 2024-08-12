package tadoclient.apis.verify

import tadoclient.models.Device
import tadoclient.models.DeviceBase
import kotlin.test.assertNotEquals

fun verifyDevice(device: Device, context:String, parentName:String = "Device") {
    // some properties are only available for specific devices, so they are allowed to be null for our test
    verifyAny(device, context, parentName, listOf("mountingState", "mountingStateWithError", "batteryState", "orientation", "childLockEnabled", "isDriverConfigured", "inPairingMode"))
    verifyAny(device.connectionState!!, context, "$parentName.connectionState")
    verifyAny(device.characteristics!!, context, "$parentName.characteristics")
    assertNotEquals(0, device.characteristics?.capabilities?.size)
//    verifyAny(device.mountingState!!, context, "$parentName.mountingState")
}

fun verifyDeviceBase(device: DeviceBase, context:String, parentName:String = "DeviceBase") {
    // some properties are only available for specific devices, so they are allowed to be null for our test
    verifyAny(device, context, parentName, listOf("mountingState", "mountingStateWithError", "batteryState", "orientation", "childLockEnabled", "isDriverConfigured", "inPairingMode"))
    verifyAny(device.connectionState!!, context, "$parentName.connectionState")
    verifyAny(device.characteristics!!, context, "$parentName.characteristics")
    assertNotEquals(0, device.characteristics?.capabilities?.size)
//    verifyAny(device.mountingState!!, context, "$parentName.mountingState")
}