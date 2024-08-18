package tadoclient.apis.verify

import tadoclient.models.*

fun verifyHome(home:Home, context:String, parentName:String = "Home") {
    val typeName = "Home"
    verifyNested(home, context, parentName, typeName,
        nullAllowedProperties = listOf(
            "$typeName.partner",
            "$typeName.contactDetails.name",
            "$typeName.contactDetails.email",
            "$typeName.contactDetails.phone",
            "$typeName.address.addressLine1",
            "$typeName.address.addressLine2",
            "$typeName.address.city",
            "$typeName.address.state",
            "$typeName.address.country"),

        emptyCollectionAllowedProperties = listOf("$typeName.skills"))
}

fun verifyAirComfort(airComfort: AirComfort, context:String, parentName:String = "AirComfort") {
    val typeName = "AirComfort"
    verifyNested(airComfort, context, parentName, typeName)
}

fun verifyHeatingSystem(heatingSystem: HeatingSystem, context:String, parentName:String = "HeatingSystem") {
    val typeName = "HeatingSystem"
    verifyNested(heatingSystem, context, parentName, typeName,
        nullAllowedProperties = listOf(
            "$typeName.boiler.id",
            "$typeName.boiler.found"))
}

fun verifyWeather(weather: Weather, context:String, parentName:String = "Weather") {
    val typeName = "Weather"
    verifyNested(weather, context, parentName, typeName)
}



