package tadoclient.apis

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestClient
import tadoclient.apis.verify.*
import tadoclient.models.*
import tadodemo.Application
import kotlin.test.*

@SpringBootTest(classes = arrayOf( Application::class))
@ActiveProfiles("test")
class ZoneControlApi_IT(
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
) {
    val tadoZoneControlAPI = ZoneControlApi(tadoRestClient)

    companion object {
        var zoneOverlayBeforeTest: Overlay? = null
    }

    // https://stackoverflow.com/questions/51612019/kotlin-how-to-manage-beforeclass-static-method-in-springboottest

    // capture the overlay status before we start running the tests
    @BeforeAll
    fun before()  = try {
        zoneOverlayBeforeTest = tadoZoneControlAPI.getZoneOverlay(HOME_ID, ZONE_ID)
    } catch (e: Exception) {
        // ignore
    }

    // reset the overlay status to the state it had before we started running the tests
    @AfterAll()
    fun after() {
        try {
            tadoZoneControlAPI.deleteZoneOverlay(HOME_ID, ZONE_ID)
        } catch (e: Exception) {
            // ignore
        }
        if (zoneOverlayBeforeTest != null) {
            val newZoneOverlay = OverlayInput(
                setting = ZoneSetting(
                    type = zoneOverlayBeforeTest?.setting?.type,
                    power = zoneOverlayBeforeTest?.setting?.power,
                    temperature = if (zoneOverlayBeforeTest?.setting?.power == Power.ON) Temperature(celsius = zoneOverlayBeforeTest?.setting?.temperature?.celsius) else null
                ),
                termination = OverlayInputTermination(
                    typeSkillBasedApp = zoneOverlayBeforeTest?.termination?.typeSkillBasedApp,
                    durationInSeconds = if (zoneOverlayBeforeTest?.termination?.typeSkillBasedApp == TypeSkillBasedApp.TIMER) zoneOverlayBeforeTest?.termination?.remainingTimeInSeconds else null
                )
            )
            // 422 Unprocessable Entity: "{"errors":[{"code":"temperature.missingCelsiusOrFahrenheit","title":"neither celsius nor fahrenheit temperatures provided"}]}"
            tadoZoneControlAPI.putZoneOverlay(HOME_ID, ZONE_ID, newZoneOverlay)
        }
    }

    // returns 404 when no overlay is set
//    @Test
//    fun getZoneOverlay() {
//        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/overlay"
//        val zoneOverlay = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
//            tadoZoneControlAPI.getZoneOverlay(HOME_ID, ZONE_ID)
//        }
//        assertNotNull(zoneOverlay)
//        verifyZoneOverlay(zoneOverlay, endpoint)
//    }

    // result:
    // ZoneOverlay(type=MANUAL, setting=ZoneSetting(power=ON, type=HEATING, temperature=ZoneSettingTemperature(celsius=18.8, fahrenheit=65.84)),
    // termination=ZoneOverlayTermination(type=MANUAL, durationInSeconds=null, remainingTimeInSeconds=null, typeSkillBasedApp=MANUAL, expiry=null, projectedExpiry=null))
    // Text in app: Until you resume schedule
    @Test
    fun putZoneOverlay_manual_on() {
        // first delete any existing overlay
        tadoZoneControlAPI.deleteZoneOverlay(HOME_ID, ZONE_ID)

        // set the overlay
        val zoneOverlay = OverlayInput(
            setting = ZoneSetting(
                type = ZoneType.HEATING,
                power = Power.ON,
                temperature = Temperature(celsius = 18.8f)
            ),
            termination = OverlayInputTermination(typeSkillBasedApp = TypeSkillBasedApp.MANUAL)
        )
        val result = tadoZoneControlAPI.putZoneOverlay(HOME_ID, ZONE_ID, zoneOverlay)
//        System.out.println("result of put overlay - manual ON")
//        System.out.println(result)
    }

    // Result:
    // Overlay(type=MANUAL, setting=ZoneSetting(power=OFF, type=HEATING, temperature=null),
    // termination=OverlayTermination(type=MANUAL, durationInSeconds=null, remainingTimeInSeconds=null, typeSkillBasedApp=MANUAL, expiry=null, projectedExpiry=null))
    @Test
    fun putZoneOverlay_manual_off() {
        // first delete any existing overlay
        tadoZoneControlAPI.deleteZoneOverlay(HOME_ID, ZONE_ID)

        // set the overlay
        val zoneOverlay = OverlayInput(
            setting = ZoneSetting(
                type = ZoneType.HEATING,
                power = Power.OFF),
            termination = OverlayInputTermination(typeSkillBasedApp = TypeSkillBasedApp.MANUAL)
        )
        val result = tadoZoneControlAPI.putZoneOverlay(HOME_ID, ZONE_ID, zoneOverlay)
        System.out.println("result of put overlay - manual OFF")
        System.out.println(result)
    }

    // Result:
    // ZoneOverlay(type=MANUAL, setting=ZoneSetting(power=ON, type=HEATING, temperature=ZoneSettingTemperature(celsius=18.8, fahrenheit=65.84)),
    // termination=ZoneOverlayTermination(type=TADO_MODE, durationInSeconds=null, remainingTimeInSeconds=null, typeSkillBasedApp=TADO_MODE, expiry=null, projectedExpiry=null))
    // Text in app: Active indefinitely

    // Type tado_mode set when temperature set via thermostat.
    // Seems to set the projected expiry and that is probably because of the configured defaultOverlay
    // Text in app: Until 10:25 PM while in Home Mode
    // overlay: {
    //                "type": "MANUAL",
    //                "setting": {
    //                    "type": "HEATING",
    //                    "power": "ON",
    //                    "temperature": {
    //                        "celsius": 6.00,
    //                        "fahrenheit": 42.80
    //                    }
    //                },
    //                "termination": {
    //                    "type": "TADO_MODE",
    //                    "typeSkillBasedApp": "TADO_MODE",
    //                    "projectedExpiry": "2024-08-16T20:25:00Z"
    //                }
    //            }
    @Test
    fun putZoneOverlay_tado_mode() {
        // first delete any existing overlay
        tadoZoneControlAPI.deleteZoneOverlay(HOME_ID, ZONE_ID)

        // set the overlay
        val zoneOverlay = OverlayInput(
            setting = ZoneSetting(
                type = ZoneType.HEATING,
                power = Power.ON,
                temperature = Temperature(celsius = 18.8f)
            ),
            termination = OverlayInputTermination(typeSkillBasedApp = TypeSkillBasedApp.TADO_MODE)
        )
        val result = tadoZoneControlAPI.putZoneOverlay(HOME_ID, ZONE_ID, zoneOverlay)
        System.out.println("result of put overlay - tado_mode")
        System.out.println(result)
    }


    // result:
    // ZoneOverlay(type=MANUAL, setting=ZoneSetting(power=ON, type=HEATING, temperature=ZoneSettingTemperature(celsius=18.8, fahrenheit=65.84)),
    // termination=ZoneOverlayTermination(type=TIMER, durationInSeconds=1800, remainingTimeInSeconds=2033, typeSkillBasedApp=NEXT_TIME_BLOCK, expiry=2024-08-14T20:00:00Z, projectedExpiry=2024-08-14T20:00:00Z))
    @Test
    fun putZoneOverlay_nextTimeBlock() {
        // first delete any existing overlay
        tadoZoneControlAPI.deleteZoneOverlay(HOME_ID, ZONE_ID)

        // set the overlay
        val zoneOverlay = OverlayInput(
            setting = ZoneSetting(
                type = ZoneType.HEATING,
                power = Power.ON,
                temperature = Temperature(celsius = 18.8f)
            ),
            termination = OverlayInputTermination(typeSkillBasedApp = TypeSkillBasedApp.NEXT_TIME_BLOCK)
        )
        val result = tadoZoneControlAPI.putZoneOverlay(HOME_ID, ZONE_ID, zoneOverlay)
//        System.out.println("result of put overlay - next time block")
//        System.out.println(result)
    }

    // Result:
    // ZoneOverlay(type=MANUAL, setting=ZoneSetting(power=ON, type=HEATING, temperature=ZoneSettingTemperature(celsius=18.8, fahrenheit=65.84)),
    // termination=ZoneOverlayTermination(type=TIMER, durationInSeconds=1400, remainingTimeInSeconds=1399, typeSkillBasedApp=TIMER, expiry=2024-08-14T19:49:24Z, projectedExpiry=2024-08-14T19:49:24Z))
    @Test
    fun putZoneOverlay_timer() {
        // first delete any existing overlay
        tadoZoneControlAPI.deleteZoneOverlay(HOME_ID, ZONE_ID)

        // set the overlay
        val zoneOverlay = OverlayInput(
            setting = ZoneSetting(
                type = ZoneType.HEATING,
                power = Power.ON,
                temperature = Temperature(celsius = 18.8f)
            ),
            termination = OverlayInputTermination(
                typeSkillBasedApp = TypeSkillBasedApp.TIMER,
                durationInSeconds = 1000)
        )
        val result = tadoZoneControlAPI.putZoneOverlay(HOME_ID, ZONE_ID, zoneOverlay)
//        System.out.println("result of put overlay - timer")
//        System.out.println(result)
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
            tadoZoneControlAPI.getZoneTimetableBlocks(HOME_ID, ZONE_ID, TimetableTypeId._1)
        }
        assertNotNull(timetableBlocks)
        assertNotEquals(0, timetableBlocks.size)
        verifyTimetableBlock(timetableBlocks[0], endpoint)
    }

    @Test
    fun getZoneTimetableBlocksByDayType() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/schedules/timetables/{timetableTypeId}/blocks/{dayType}"
        val timetableBlocks = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoZoneControlAPI.getZoneTimetableBlocksByDayType(HOME_ID, ZONE_ID, TimetableTypeId._1, DayType.SATURDAY)
        }
        assertNotNull(timetableBlocks)
        assertNotEquals(0, timetableBlocks.size)
        verifyTimetableBlock(timetableBlocks[0], endpoint)
    }
}