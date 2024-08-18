package tadoclient.apis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestClient
import tadoclient.apis.verify.*
import tadodemo.Application
import kotlin.test.Test
import kotlin.test.assertNotEquals

@SpringBootTest(classes = arrayOf( Application::class))
@ActiveProfiles("test")
class DeviceApi_IT (
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
) {
    val tadoDeviceAPI = DeviceApi(tadoRestClient)

    @Test
    fun getTemperatureOffset() {
        val endpoint = "GET /devices/{deviceId}/temperatureOffset"
        val offset = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoDeviceAPI.getTemperatureOffset(VA_DEVICE)
        }
        val typeName = "TemperatureOffset"
        verifyNested(offset, endpoint, typeName, typeName)
    }

    // TODO test PUT temperatureOffset

    @Test
    fun getDevices() {
        val endpoint = "GET /homes/{homeId}/devices"
        val devices = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoDeviceAPI.getDevices(HOME_ID)
        }

        // check devices
        assertNotEquals(0, devices.size)
        devices.forEachIndexed { i, elem -> verifyDevice(elem, endpoint, "response[$i]") }
    }

    @Test
    fun identifyDevice() {
        val endpoint = "POST /devices/{deviceId}/identify"
        assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoDeviceAPI.identifyDevice(VA_DEVICE)
        }
        // no response, so nothing to verify
    }

    @Test
    fun getDeviceList() {
        val endpoint = "GET /homes/{homeId}/deviceList"
        val deviceList = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoDeviceAPI.getDeviceList(HOME_ID)
        }

        assertNotEquals(0, deviceList.propertyEntries?.size)
        deviceList.propertyEntries?.forEachIndexed { i, elem ->
            verifyDeviceListItem(
                elem,
                endpoint,
                "response.entries[$i]"
            )
        }
    }

    @Test
    fun getControl() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/control"
        val zoneControl = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoDeviceAPI.getZoneControl(HOME_ID, ZONE_ID)
        }

        val typeName = "ZoneControl"
        verifyNested(zoneControl, endpoint, endpoint, typeName,
            nullAllowedProperties = listOf(
                "$typeName.duties.driver",
                "$typeName.duties.drivers",
                "$typeName.duties.leader",
                "$typeName.duties.leaders",
                "$typeName.duties.ui",
                "$typeName.duties.uis"),
            stopAtProperties = listOf(
                "$typeName.duties.driver",
                "$typeName.duties.drivers",
                "$typeName.duties.leader",
                "$typeName.duties.leaders",
                "$typeName.duties.ui",
                "$typeName.duties.uis"))

        // verify duties.driver
        zoneControl.duties?.driver?.let {
            verifyDevice(it, endpoint, "$endpoint.duties.driver")
        }

        // verify duties.leader
        zoneControl.duties?.leader?.let {
            verifyDevice(it, endpoint, "$endpoint.duties.leader")
        }

        // verify duties.ui
        zoneControl.duties?.ui?.let {
            verifyDevice(it, endpoint, "$endpoint.duties.ui")
        }

        // verify duties.drivers
        zoneControl.duties?.drivers?.let {
            it.forEachIndexed { i, device -> verifyDevice(device, endpoint, "$endpoint.duties.drivers[$i]")}
        }

        // verify duties.leaders
        zoneControl.duties?.leaders?.let {
            it.forEachIndexed { i, device -> verifyDevice(device, endpoint, "$endpoint.duties.leaders[$i]")}
        }

        // verify duties.leaders
        zoneControl.duties?.uis?.let {
            it.forEachIndexed { i, device -> verifyDevice(device, endpoint, "$endpoint.uis.uis[$i]") }
        }
    }

    @Test
    fun getMeasuringDevice() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/measuringDevice"
        val device = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoDeviceAPI.getZoneMeasuringDevice(HOME_ID, ZONE_ID)
        }
        verifyDevice(device, endpoint)
    }

    // TODO test PUT measuringDevice

}