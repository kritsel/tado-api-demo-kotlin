package tadoclient.apis.verify

import tadoclient.models.*

fun verifyZoneOverlay(zoneType: ZoneType, zoneOverlay: ZoneOverlay, context:String, fullParentName:String = "ZoneOverlay"){
    val typeName = "ZoneOverlay"
    verifyNested(zoneOverlay, context, fullParentName, typeName,
        // an overlay can be indefinite, meaning that none of time related terminiation properties will have a value
        nullAllowedProperties = listOf(
            "$typeName.termination.durationInSeconds",
            "$typeName.termination.remainingTimeInSeconds",
            "$typeName.termination.expiry",
            "$typeName.termination.projectedExpiry"),
        stopAtProperties = listOf("$typeName.setting"))

    // verify the ZoneSetting of this overlay
    verifyZoneSetting(zoneType, zoneOverlay.setting!!, context, "$fullParentName.setting")
}

fun verifyZoneAwayConfiguration(zoneType:ZoneType, zoneAwayConfiguration: ZoneAwayConfiguration, context:String, parentName:String = "ZoneAwayConfiguration") {
    val typeName = "ZoneAwayConfiguration"
    verifyNested(zoneAwayConfiguration, context, parentName, typeName,
        nullAllowedProperties = listOf("$typeName.comfortLevel"),
        stopAtProperties = listOf("$typeName.setting"))

    // verify setting
    verifyZoneSetting(zoneType, zoneAwayConfiguration.setting!!, context, "$parentName.setting")
}

fun verifyTimetableType(timetableType: TimetableType, context:String, parentName:String = "TimetableType") {
    verifyNested(timetableType, context, parentName, "TimetableType")
}

fun verifyTimetableBlock(zoneType:ZoneType, timetableBlock: TimetableBlock, context:String, parentName:String = "TimetableBlock") {
    val typeName = "TimetableBlock"
    verifyNested(timetableBlock, context, parentName, typeName, stopAtProperties = listOf("$typeName.setting"))

    // verify setting
    verifyZoneSetting(zoneType, timetableBlock.setting!!, context, "$parentName.setting")
}

