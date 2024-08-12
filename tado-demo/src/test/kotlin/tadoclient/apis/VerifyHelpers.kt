package tadoclient.apis

import tadoclient.models.Home
import tadoclient.models.HomeState
import tadoclient.models.MobileDevice
import tadoclient.models.User
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

fun verifyUser(user: User) {
    // check user
    assertNotNull(user)
    assertNotNull(user.id)
    assertNotNull(user.name)
    assertNotNull(user.locale)
    assertNotNull(user.email)
    assertNotNull(user.username)

    // check user.homes
    assertNotNull(user.homes)
    assertNotEquals(0, user.homes?.size )
    assertNotNull(user.homes!![0].id)
    assertNotNull(user.homes!![0].name)

    // check user.mobileDevices
    assertNotNull(user.mobileDevices)
    assertNotEquals(0, user.mobileDevices?.size )
    assertNotNull(user.mobileDevices!![0])
    verifyMobileDevice(user.mobileDevices!![0])
}

fun verifyMobileDevice(mobileDevice: MobileDevice) {
    assertNotNull(mobileDevice.id)
    assertNotNull(mobileDevice.name)

    // check user.mobileDevices.deviceMetadata
    assertNotNull(mobileDevice.deviceMetadata)
    assertNotNull(mobileDevice.deviceMetadata?.locale)
    assertNotNull(mobileDevice.deviceMetadata?.model)
    assertNotNull(mobileDevice.deviceMetadata?.osVersion)
    assertNotNull(mobileDevice.deviceMetadata?.platform)

    // check user.mobileDevices.settings
    assertNotNull(mobileDevice.settings)
    assertNotNull(mobileDevice.settings?.geoTrackingEnabled)
    assertNotNull(mobileDevice.settings?.specialOffersEnabled)
}

fun verifyHome(home:Home) {
    assertNotNull(home.id)
    assertNotNull(home.name)
}

fun verifyHomeState(homeState: HomeState) {
    assertNotNull(homeState.presence)
    assertNotNull(homeState.presenceLocked)
}