package tadoclient.apis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestClient
import tadoclient.apis.verify.*
import tadoclient.models.ZoneType
import tadodemo.Application
import kotlin.test.Test
import kotlin.test.assertNotEquals

@SpringBootTest(classes = arrayOf( Application::class))
@ActiveProfiles("test")
class ZoneApi_IT(
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
) {
    val tadoZoneAPI = ZoneApi(tadoRestClient)

    @Test
    fun getZones() {
        val endpoint = "GET /homes/{homeId}/zones"
        val zones = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneAPI.getZones(HOME_ID)
        }
        assertNotEquals(0, zones.size)
        verifyZone(zones[0], endpoint, "response[0]")
    }

    @Test
    fun getZoneCapabilities_HeatingZone() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/capabilities"
        val zoneCapabilities = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneAPI.getZoneCapabilities(HOME_ID, ZONE_ID)
        }
        verifyZoneCapabilities(ZoneType.HEATING, zoneCapabilities, endpoint)
    }

    @Test
    fun getZoneCapabilities_HotWaterZone() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/capabilities"
        val zoneCapabilities = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneAPI.getZoneCapabilities(HOME_ID, 0)
        }
        verifyZoneCapabilities(ZoneType.HOT_WATER, zoneCapabilities, endpoint)
    }

    @Test
    fun getZoneCapabilities_404() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/capabilities"
        assertHttpErrorStatus(failMessage404(endpoint), HttpStatus.NOT_FOUND) {
            tadoZoneAPI.getZoneCapabilities(HOME_ID, 999)
        }
    }

    @Test
    fun getZoneState_Heating() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/state"
        val zoneState = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneAPI.getZoneState(HOME_ID, ZONE_ID)
        }
        verifyZoneState(ZoneType.HEATING, zoneState, endpoint)
    }


    @Test
    fun getZoneState_HotWater() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/state"
        val zoneState = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneAPI.getZoneState(HOME_ID, 0)
        }
        verifyZoneState(ZoneType.HOT_WATER, zoneState, endpoint)
    }

    @Test
    fun getZoneState_404() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/state"
        assertHttpErrorStatus(failMessage404(endpoint), HttpStatus.NOT_FOUND) {
            tadoZoneAPI.getZoneState(HOME_ID, 999)
        }
    }

    @Test
    fun getZoneStates() {
        val endpoint = "GET /homes/{homeId}/zoneStates"
        val zoneStates = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneAPI.getZoneStates(HOME_ID)
        }
        verifyZoneState(ZoneType.HEATING, zoneStates.zoneStates?.get("$ZONE_ID")!!, endpoint)
        verifyZoneState(ZoneType.HOT_WATER, zoneStates.zoneStates?.get("0")!!, endpoint)
    }

}