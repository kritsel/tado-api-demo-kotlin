package tadoclient.apis.verify

import com.sun.jdi.connect.spi.TransportService
import tadoclient.models.*
import kotlin.test.assertNotEquals

fun verifyZone(zone: Zone, context:String, fullParentName:String = "Zone") {
    val typeName = "Zone"
    verifyNested(zone, context, fullParentName, typeName,
        stopAtProperties = listOf("$typeName.devices"))

    // devices
    assertNotEquals(0, zone.devices!!.size)
    zone.devices?.forEachIndexed() {i, elem -> verifyDeviceExtra(elem, context, "$fullParentName.devices[$i]")}
}

fun verifyZoneCapabilities(zoneType: ZoneType, zoneCapabilities: ZoneCapabilities, context:String, parentName:String = "ZoneCapabilities") {
    val typeName="ZoneCapabilities"
    when(zoneType) {
        ZoneType.HEATING -> {
            verifyNested(zoneCapabilities, context, parentName, parentName, nullAllowedProperties = listOf("$typeName.canSetTemperature", "$typeName.AUTO", "$typeName.COOL", "$typeName.HEAT", "$typeName.DRY", "$typeName.FAN", "$typeName.initialStates"))
        }
        ZoneType.AIR_CONDITIONING -> {
            verifyNested(zoneCapabilities, context, parentName, parentName, nullAllowedProperties = listOf("$typeName.canSetTemperature", "$typeName.temperatures"))
        }
        ZoneType.HOT_WATER -> {
            verifyNested(zoneCapabilities, context, parentName, parentName, nullAllowedProperties = listOf("$typeName.temperatures", "$typeName.AUTO", "$typeName.COOL", "$typeName.HEAT", "$typeName.DRY", "$typeName.FAN", "$typeName.initialStates"))
        }
    }
}

fun verifyZoneState(zoneType:ZoneType, zoneState: ZoneState, context:String, fullParentName:String = "ZoneState"){
    val typeName = "ZoneState"

    // properties which can be null for any zoneType
    val basicNullAllowedProperties = listOf(
        "$typeName.geolocationOverrideDisableTime",
        "$typeName.preparation",
        "$typeName.overlay",
        "$typeName.overlayType",
        "$typeName.nextScheduleChange",
        "$typeName.openWindow")

    // properties which should not be inspected by verifyNested for any zoneType
    val basicStopAtProperties = listOf(
        "$typeName.overlay",
        "$typeName.setting",
        "$typeName.nextScheduleChange.setting")

    when (zoneType) {

        ZoneType.HEATING -> {
            verifyNested(zoneState, context, fullParentName, typeName,
                nullAllowedProperties = basicNullAllowedProperties,
                stopAtProperties = basicStopAtProperties)
        }

        ZoneType.HOT_WATER -> {
            val hotWaterNullAllowedProperties = mutableListOf(
                "$typeName.nextTimeBlock",
                "$typeName.activityDataPoints.heatingPower",
                "$typeName.sensorDataPoints.humidity",
                "$typeName.sensorDataPoints.insideTemperature")
            hotWaterNullAllowedProperties.addAll(basicNullAllowedProperties)
            verifyNested(zoneState, context, fullParentName, typeName,
                nullAllowedProperties = hotWaterNullAllowedProperties,
                stopAtProperties = basicStopAtProperties)
        }

        // unknonw
        ZoneType.AIR_CONDITIONING -> {}
    }

//




    zoneState.overlay?.let {
        verifyZoneOverlay(zoneType, it, context, "$fullParentName.overlay")
    }

    zoneState.setting?.let {
        verifyZoneSetting(zoneType, it, context, "$fullParentName.setting")
    }

    zoneState.nextScheduleChange?.setting?.let {
        verifyZoneSetting(zoneType, it, context, "$fullParentName.nextScheduleChange.setting")
    }
}

fun verifyZoneSetting(zoneType: ZoneType, zoneSetting: ZoneSetting, context:String, fullParentName:String = "ZoneSetting"){
    val typeName = "ZoneSetting"
    when (zoneType) {

        ZoneType.HEATING -> {
            // start with the properties which are only applicable to an AIR_CONDITIONING zone
            var nullAllowedProperties = mutableListOf(
                "$typeName.fanLevel",
                "$typeName.verticalSwing",
                "$typeName.horizontalSwing",
                "$typeName.light",
                "$typeName.mode"
            )
            // when the zone is OFF, there will not be a temperature value either
            if (zoneSetting.power == Power.OFF) {
                nullAllowedProperties.add("$typeName.temperature")
            }
            verifyNested(zoneSetting, context, fullParentName, typeName, nullAllowedProperties = nullAllowedProperties)
        }

        // only type and power are expected to have a value, all other properties are allowed to be null
        ZoneType.HOT_WATER -> {
            verifyNested(zoneSetting, context, fullParentName, typeName, nullAllowedProperties = mutableListOf(
                "$typeName.fanLevel",
                "$typeName.verticalSwing",
                "$typeName.horizontalSwing",
                "$typeName.light",
                "$typeName.mode",
                "$typeName.temperature"
            ))
        }

        // unknown
        ZoneType.AIR_CONDITIONING -> { }
    }
}






