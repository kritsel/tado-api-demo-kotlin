package tadoclient.apis.verify

import tadoclient.models.*
import kotlin.test.assertNotNull

fun verifyUser(user:User, context:String, parentName:String = "User") {
    verifyAny(user, context, parentName)

    assertNotNull(user.homes)
    assertNotNull(user.homes!![0])
    verifyHomeBase(user.homes!![0], context, "$parentName.homes[0]")

    assertNotNull(user.mobileDevices)
    assertNotNull(user.mobileDevices!![0])
    verifyMobileDevice(user.mobileDevices!![0], context, "$parentName.mobileDevices[0]")
}

fun verifyHomeBase(homeBase:HomeBase, context:String, parentName:String = "HomeBase") {
    verifyAny(homeBase, context, parentName)
}



