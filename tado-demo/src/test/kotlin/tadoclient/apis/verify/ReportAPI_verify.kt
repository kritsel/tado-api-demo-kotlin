package tadoclient.apis.verify

import tadoclient.models.*
import kotlin.test.assertNotEquals

fun verifyDayReport(dayReport:DayReport, context:String, parentName:String = "DayReport") {
    verifyAny(dayReport, context, parentName)

    verifyAny(dayReport.interval!!, context, "$parentName.interval")

    verifyAny(dayReport.measuredData!!, context, "$parentName.measuredData")
    verifyAny(dayReport.measuredData!!.measuringDeviceConnected!!, context, "$parentName.measuredData.measuringDeviceConnected")
    assertNotEquals(0, dayReport.measuredData!!.measuringDeviceConnected!!.dataIntervals!!.size)
    verifyAny(dayReport.measuredData!!.measuringDeviceConnected!!.dataIntervals!![0], context, "$parentName.measuredData.measuringDeviceConnected.dataIntervals[0]")
    verifyAny(dayReport.measuredData!!.insideTemperature!!, context, "$parentName.measuredData.insideTemperature")
    verifyAny(dayReport.measuredData!!.insideTemperature!!.min!!, context, "$parentName.measuredData.insideTemperature.min")
    verifyAny(dayReport.measuredData!!.insideTemperature!!.max!!, context, "$parentName.measuredData.insideTemperature.max")
    assertNotEquals(0, dayReport.measuredData!!.insideTemperature!!.dataPoints!!.size)
    verifyAny(dayReport.measuredData!!.insideTemperature!!.dataPoints!![0], context, "$parentName.measuredData.insideTemperature.dataPoints[0]")
    verifyAny(dayReport.measuredData!!.insideTemperature!!.dataPoints!![0].value!!, context, "$parentName.measuredData.insideTemperature.dataPoints[0].value")
    verifyAny(dayReport.measuredData!!.humidity!!, context, "$parentName.measuredData.humidity")
    assertNotEquals(0, dayReport.measuredData!!.humidity!!.dataPoints!!.size)
    verifyAny(dayReport.measuredData!!.humidity!!.dataPoints!![0], context, "$parentName.measuredData.humidity.dataPoints[0]")

    verifyAny(dayReport.stripes!!, context, "$parentName.measuredData")
    assertNotEquals(0, dayReport.stripes!!.dataIntervals!!.size)
    verifyAny(dayReport.stripes!!.dataIntervals!![0], context, "$parentName.stripes.dataIntervals[0]")
    verifyAny(dayReport.stripes!!.dataIntervals!![0].value!!, context, "$parentName.stripes.dataIntervals[0].value")
    verifyAny(dayReport.stripes!!.dataIntervals!![0].value!!.setting!!, context, "$parentName.stripes.dataIntervals[0].value.setting")
    verifyAny(dayReport.stripes!!.dataIntervals!![0].value!!.setting!!.temperature!!, context, "$parentName.stripes.dataIntervals[0].value.setting.temperature")

    verifyAny(dayReport.callForHeat!!, context, "$parentName.callForHeat")
    assertNotEquals(0, dayReport.callForHeat!!.dataIntervals!!.size)
    verifyAny(dayReport.callForHeat!!.dataIntervals!![0], context, "$parentName.callForHeat.dataIntervals[0]")

    verifyAny(dayReport.hotWaterProduction!!, context, "$parentName.hotWaterProduction")
    assertNotEquals(0, dayReport.hotWaterProduction!!.dataIntervals!!.size)
    verifyAny(dayReport.hotWaterProduction!!.dataIntervals!![0], context, "$parentName.hotWaterProduction.dataIntervals[0]")

    verifyAny(dayReport.weather!!, context, "$parentName.weather")
    verifyAny(dayReport.weather!!.condition!!, context, "$parentName.weather.condition")
    assertNotEquals(0, dayReport.weather!!.condition!!.dataIntervals!!.size)
    verifyAny(dayReport.weather!!.condition!!.dataIntervals!![0], context, "$parentName.weather.condition.dataIntervals[0]")
    verifyAny(dayReport.weather!!.condition!!.dataIntervals!![0].value!!, context, "$parentName.weather.condition.dataIntervals[0].value")
    verifyAny(dayReport.weather!!.condition!!.dataIntervals!![0].value!!.temperature!!, context, "$parentName.weather.condition.dataIntervals[0].value.temperature")

    verifyAny(dayReport.weather!!.sunny!!, context, "$parentName.weather.sunny")
    assertNotEquals(0, dayReport.weather!!.sunny!!.dataIntervals!!.size)
    verifyAny(dayReport.weather!!.sunny!!.dataIntervals!![0], context, "$parentName.weather.sunny.dataIntervals[0]")

    verifyAny(dayReport.weather!!.slots!!, context, "$parentName.weather.slots")
    verifyAny(dayReport.weather!!.slots!!.slots!!, context, "$parentName.weather.slots.slots")
    verifyAny(dayReport.weather!!.slots!!.slots!!.`04colon00`!!, context, "$parentName.weather.slots.slots.04:00")
    verifyAny(dayReport.weather!!.slots!!.slots!!.`04colon00`!!.temperature!!, context, "$parentName.weather.slots.slots.'04:00'.temperature")
}




