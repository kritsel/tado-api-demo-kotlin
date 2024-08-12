package tadoclient.apis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestClient
import tadoclient.apis.verify.*
import tadodemo.Application
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

@SpringBootTest(classes = arrayOf( Application::class))
class ZoneApi_IT(
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
) {
    val tadoZoneAPI = ZoneApi(tadoRestClient)

    @Test
    fun getZones() {
        val endpoint = "GET /homes/{homeId}/zones"
        val zones = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneAPI.getZones(HOME_ID)
        }
        assertNotNull(zones)
        assertNotEquals(0, zones.size)
        verifyZone(zones[0], endpoint, "response[0]")
    }

    @Test
    fun getZoneCapabilities_HeatingZone() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/capabilities"
        val zoneCapabilities = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneAPI.getZoneCapabilities(HOME_ID, ZONE_ID)
        }
        assertNotNull(zoneCapabilities)
        verifyZoneCapabilities_HeatingZone(zoneCapabilities, endpoint)
    }

    @Test
    fun getZoneCapabilities_HotWaterZone() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/capabilities"
        val zoneCapabilities = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneAPI.getZoneCapabilities(HOME_ID, 0)
        }
        assertNotNull(zoneCapabilities)
        verifyZoneCapabilities_HotWaterZone(zoneCapabilities, endpoint)
    }

    @Test
    fun getZoneState() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/state"
        val zoneState = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneAPI.getZoneState(HOME_ID, ZONE_ID)
        }
        assertNotNull(zoneState)
        verifyZoneState(zoneState, endpoint)
    }

    @Test
    fun getZoneStates() {
        val endpoint = "GET /homes/{homeId}/zoneStates"
        val zoneStates = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneAPI.getZoneStates(HOME_ID)
        }
        assertNotNull(zoneStates)
        verifyZoneState(zoneStates.zoneStates?.get("$ZONE_ID")!!, endpoint)
    }

}