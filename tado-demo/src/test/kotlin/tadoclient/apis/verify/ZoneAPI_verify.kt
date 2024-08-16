package tadoclient.apis.verify

import com.sun.jdi.connect.spi.TransportService
import tadoclient.models.Zone
import tadoclient.models.ZoneCapabilities
import tadoclient.models.ZoneState
import kotlin.test.assertNotEquals

fun verifyZone(zone: Zone, context:String, parentName:String = "Zone") {
    verifyAny(zone, context, parentName)
    verifyAny(zone.openWindowDetection!!, context, "$parentName.openWindowDetection")
    verifyAny(zone.dazzleMode!!, context, "$parentName.dazzleMode")

    assertNotEquals(0, zone.deviceTypes?.size)

    assertNotEquals(0, zone.devices?.size)
    verifyDevice(zone.devices?.get(0)!!, context, "$parentName.devices[0]")
}

fun verifyZoneCapabilities_HeatingZone(zoneCapabilities: ZoneCapabilities, context:String, parentName:String = "ZoneCapabilities") {
    // canSetTemperature is allowed to be null
    verifyAny(zoneCapabilities, context, parentName, listOf("canSetTemperature"))
    verifyAny(zoneCapabilities.temperatures!!, context, "$parentName.temperatures")
}

fun verifyZoneCapabilities_HotWaterZone(zoneCapabilities: ZoneCapabilities, context:String, parentName:String = "ZoneCapabilities") {
    // temperatures is allowed to be null
    verifyAny(zoneCapabilities, context, parentName, listOf("temperatures"))
}

fun verifyZoneState(zoneState: ZoneState, context:String, parentName:String = "ZoneState") {
    // geolocationOverrideDisableTime and preparation and overlay are allowed to be null
    verifyAny(zoneState, context, parentName, listOf("geolocationOverrideDisableTime", "preparation", "overlay", "overlayType", "nextScheduleChange", "openWindow"))

    verifyAny(zoneState.setting!!, context, "$parentName.setting", listOf("temperature"))
//    verifyAny(zoneState.setting!!.temperature!!, context, "$parentName.setting.temperature")


    // TODO: find a better way to test this
//    verifyAny(zoneState.overlay!!, context, "$parentName.overlay", listOf("openWindow", "nextScheduleChange"))
//    verifyAny(zoneState.overlay!!.nextScheduleChange!!, context, "$parentName.overlay.nextScheduleChange")
//    verifyAny(zoneState.overlay!!.nextScheduleChange!!.setting!!, context, "$parentName.overlay.nextScheduleChange.setting")
//    verifyAny(zoneState.overlay!!.nextScheduleChange!!.setting!!.temperature!!, context, "$parentName.overlay.nextScheduleChange.setting.temperature")
//    verifyAny(zoneState.overlay!!.setting!!, context, "$parentName.overlay.setting", listOf("temperature"))
//    verifyAny(zoneState.overlay!!.setting!!.temperature!!, context, "$parentName.overlay.setting.temperature")
//    verifyAny(zoneState.overlay!!.termination!!, context, "$parentName.overlay.termination", listOf("projectedExpiry"))

    verifyAny(zoneState.nextTimeBlock!!, context, "$parentName.nextTimeBlock")

    verifyAny(zoneState.link!!, context, "$parentName.link")

    verifyAny(zoneState.activityDataPoints!!, context, "$parentName.activityDataPoints")
    verifyAny(zoneState.activityDataPoints!!.heatingPower!!, context, "$parentName.activityDataPoints.heatingPower")

    verifyAny(zoneState.sensorDataPoints!!, context, "$parentName.sensorDataPoints")
    verifyAny(zoneState.sensorDataPoints!!.insideTemperature!!, context, "$parentName.sensorDataPoints.insideTemperature")
    verifyAny(zoneState.sensorDataPoints!!.insideTemperature!!.precision!!, context, "$parentName.sensorDataPoints.insideTemperature.precision")
    verifyAny(zoneState.sensorDataPoints!!.humidity!!, context, "$parentName.sensorDataPoints.humidity")
}




