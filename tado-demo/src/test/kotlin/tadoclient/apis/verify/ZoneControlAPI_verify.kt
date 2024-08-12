package tadoclient.apis.verify

import tadoclient.models.*

fun verifyZoneOverlay(zoneOverlay: ZoneOverlay, context:String, parentName:String = "ZoneOverlay") {
    verifyAny(zoneOverlay, context, parentName)
    // TODO: figure out how to test this
    verifyAny(zoneOverlay.setting!!, context, "$parentName.setting", listOf("temperature"))
//    verifyAny(zoneOverlay.setting!!.temperature!!, context, "$parentName.setting.temperature")

    // TODO: figure out how to test projectedExpiry
    verifyAny(zoneOverlay.termination!!, context, "$parentName.termination", listOf("projectedExpiry"))
}

fun verifyZoneAwayConfiguration(zoneAwayConfiguration: ZoneAwayConfiguration, context:String, parentName:String = "ZoneAwayConfiguration") {
    verifyAny(zoneAwayConfiguration, context, parentName, listOf("comfortLevel"))
    verifyAny(zoneAwayConfiguration.setting!!, context, "$parentName.setting", listOf("temperature"))
    // TODO: figure out how to test this
//    verifyAny(zoneAwayConfiguration.setting!!.temperature!!, context, "$parentName.setting.temperature")
}

fun verifyTimetableType(timetableType: TimetableType, context:String, parentName:String = "TimetableType") {
    verifyAny(timetableType, context, parentName)
}

fun verifyTimetableBlock(timetableBlock: TimetableBlock, context:String, parentName:String = "TimetableBlock") {
    verifyAny(timetableBlock, context, parentName)
    verifyAny(timetableBlock.setting!!, context, "$parentName.setting")
    verifyAny(timetableBlock.setting!!.temperature!!, context, "$parentName.setting.temperature")
}

