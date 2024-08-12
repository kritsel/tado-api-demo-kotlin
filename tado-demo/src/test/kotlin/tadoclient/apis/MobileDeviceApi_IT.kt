package tadoclient.apis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestClient
import tadoclient.apis.verify.verifyMobileDevice
import tadodemo.Application
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

/**
 * Not tested: DELETE /homes/{homeId}/mobileDevices/{mobileDeviceId} as it would be a destructive test
 */

@SpringBootTest(classes = arrayOf( Application::class))
class MobileDeviceApi_IT(
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
) {
    val tadoMobileDeviceAPI = MobileDeviceApi(tadoRestClient)

    @Test
    fun testGetMobileDevices() {
        val endpoint = "GET /homes/{homeId}/mobileDevices"
        val mobileDevices = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoMobileDeviceAPI.getMobileDevices(HOME_ID)
        }
        assertNotNull(mobileDevices)
        assertNotEquals(0, mobileDevices.size)
        verifyMobileDevice(mobileDevices[0], endpoint, "response[0]")
    }

    @Test
    fun testGetMobileDevice() {
        val endpoint = "GET /homes/{homeId}/mobileDevices/{mobileDeviceId}"
        val mobileDevice = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoMobileDeviceAPI.getMobileDevice(HOME_ID, MOBILE_DEVICE_ID)
        }
        assertNotNull(mobileDevice)
        verifyMobileDevice(mobileDevice, endpoint)
    }
}