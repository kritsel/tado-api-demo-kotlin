package tadoclient.apis.verify

import tadoclient.models.*
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

fun verifyHome(home:Home, context:String, parentName:String = "Home") {
    // partner is allowed to be null
    verifyAny(home, context, parentName, listOf("partner"))
    verifyAny(home.contactDetails!!, context, "$parentName.contactDetails")

    // addressLine2 and state are allowed to be null
    verifyAny(home.address!!, context, "$parentName.address", listOf("addressLine2", "state"))
    verifyAny(home.geolocation!!, context, "$parentName.geolocation")
    verifyAny(home.incidentDetection!!, context, "$parentName.incidentDetection")
    verifyAny(home.temperatureUnit!!, context, "$parentName.temperatureUnit")
}

fun verifyAirComfort(airComfort: AirComfort, context:String, parentName:String = "AirComfort") {
    verifyAny(airComfort, context, parentName)
    verifyAny(airComfort.freshness!!, context, "$parentName.freshness")

    assertNotEquals(0, airComfort.comfort?.size)
    verifyAny(airComfort.comfort!![0], context, "$parentName.comfort[0]")
    verifyAny(airComfort.comfort!![0].coordinate!!, context, "$parentName.comfort[0].coordinate")
}

fun verifyHeatingSystem(heatingSystem: HeatingSystem, context:String, parentName:String = "HeatingSystem") {
    verifyAny(heatingSystem, context, parentName)
    verifyAny(heatingSystem.boiler!!, context, "$parentName.boiler", nullAllowedProperties = listOf("id", "found"))
    verifyAny(heatingSystem.underfloorHeating!!, context, "$parentName.underfloorHeating")
}

fun verifyWeather(weather: Weather, context:String, parentName:String = "Weather") {
    verifyAny(weather, context, parentName)
    verifyAny(weather.weatherState!!, context, "$parentName.weatherState")
    verifyAny(weather.solarIntensity!!, context, "$parentName.solarIntensity")
    verifyAny(weather.outsideTemperature!!, context, "$parentName.outsideTemperature")
    verifyAny(weather.outsideTemperature!!.precision!!, context, "$parentName.outsideTemperature.precision")
}


    // AirComfort:
//    // verify freshness
//    assertNotNull(airComfort.freshness)
//    assertNotNull(airComfort.freshness?.value)
//    assertNotNull(airComfort.freshness?.lastOpenWindow)
//
//    // verify comfort
//    assertNotNull(airComfort.comfort)
//    assertNotEquals(0, airComfort.comfort?.size)
//    assertNotNull(airComfort.comfort!![0])
//    assertNotNull(airComfort.comfort!![0].roomId)
//    assertNotNull(airComfort.comfort!![0].temperatureLevel)
//    assertNotNull(airComfort.comfort!![0].humidityLevel)
//    assertNotNull(airComfort.comfort!![0].coordinate)
//    assertNotNull(airComfort.comfort!![0].coordinate?.radial)
//    assertNotNull(airComfort.comfort!![0].coordinate?.angular)


