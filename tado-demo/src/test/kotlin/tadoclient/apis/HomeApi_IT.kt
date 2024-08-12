package tadoclient.apis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestClient
import tadoclient.apis.verify.verifyAirComfort
import tadoclient.apis.verify.verifyHeatingSystem
import tadoclient.apis.verify.verifyHome
import tadoclient.apis.verify.verifyWeather
import tadodemo.Application
import kotlin.test.Test
import kotlin.test.assertNotNull

@SpringBootTest(classes = arrayOf( Application::class))
class HomeApi_IT(
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
) {
    val tadoHomeAPI = HomeApi(tadoRestClient)

    @Test
    fun getHome() {
        val endpoint = "GET /homes/{homeId}"
        val home = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getHome(HOME_ID)
        }
        assertNotNull(home)
        verifyHome(home, endpoint)
    }

    @Test
    fun getAirComfort() {
        val endpoint = "GET /homes/{homeId}/airComfort"
        val airComfort = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getAirComfort(HOME_ID)
        }
        assertNotNull(airComfort)
        verifyAirComfort(airComfort, endpoint)
    }

    @Test
    fun getHeatingSystem() {
        val endpoint = "GET /homes/{homeId}/heatingSystem"
        val heatingSystem = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getHeatingSystem(HOME_ID)
        }
        assertNotNull(heatingSystem)
        verifyHeatingSystem(heatingSystem, endpoint)
    }

    @Test
    fun getWeather() {
        val endpoint = "GET /homes/{homeId}/weather"
        val weather = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getWeather(HOME_ID)
        }
        assertNotNull(weather)
        verifyWeather(weather, endpoint)
    }

}