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
class ZoneControlApi_IT(
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
) {
    val tadoZoneControlAPI = ZoneControlApi(tadoRestClient)

    @Test
    fun getZoneOverlay() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/overlay"
        val zoneOverlay = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneControlAPI.getZoneOverlay(HOME_ID, ZONE_ID)
        }
        assertNotNull(zoneOverlay)
        verifyZoneOverlay(zoneOverlay, endpoint)
    }

    @Test
    fun getZoneAwayConfiguration() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/schedules/awayConfiguration"
        val awayConfiguration = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneControlAPI.getZoneAwayConfiguration(HOME_ID, ZONE_ID)
        }
        assertNotNull(awayConfiguration)
        verifyZoneAwayConfiguration(awayConfiguration, endpoint)
    }

    @Test
    fun getZoneActiveTimetable() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/schedules/activeTimetable"
        val timetable = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneControlAPI.getZoneActiveTimetable(HOME_ID, ZONE_ID)
        }
        assertNotNull(timetable)
        verifyTimetableType(timetable, endpoint)
    }

    @Test
    fun getZoneTimetables() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/schedules/timetables"
        val timetables = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneControlAPI.getZoneTimetables(HOME_ID, ZONE_ID)
        }
        assertNotNull(timetables)
        assertNotEquals(0, timetables.size)
        verifyTimetableType(timetables[0], endpoint)
    }

    @Test
    fun getZoneTimetable() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/schedules/timetables/{timetableTypeId}"
        val timetable = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneControlAPI.getZoneTimetable(HOME_ID, ZONE_ID, 1)
        }
        assertNotNull(timetable)
        verifyTimetableType(timetable, endpoint)
    }

    @Test
    fun getZoneTimetableBlocks() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/schedules/timetables/{timetableTypeId}/blocks"
        val timetableBlocks = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneControlAPI.getZoneTimetableBlocks(HOME_ID, ZONE_ID, 1)
        }
        assertNotNull(timetableBlocks)
        assertNotEquals(0, timetableBlocks.size)
        verifyTimetableBlock(timetableBlocks[0], endpoint)
    }

    @Test
    fun getZoneTimetableBlocksByDayType() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/schedules/timetables/{timetableTypeId}/blocks/{dayType}"
        val timetableBlocks = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneControlAPI.getZoneTimetableBlocksByDayType(HOME_ID, ZONE_ID, 1, "SATURDAY")
        }
        assertNotNull(timetableBlocks)
        assertNotEquals(0, timetableBlocks.size)
        verifyTimetableBlock(timetableBlocks[0], endpoint)
    }
}