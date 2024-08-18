package tadoclient.apis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestClient
import tadoclient.apis.verify.verifyAirComfort
import tadoclient.apis.verify.verifyHeatingSystem
import tadoclient.apis.verify.verifyHome
import tadoclient.apis.verify.verifyWeather
import tadodemo.Application
import kotlin.test.Test

@SpringBootTest(classes = arrayOf( Application::class))
@ActiveProfiles("test")
class HomeApi_IT(
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
) {
    val tadoHomeAPI = HomeApi(tadoRestClient)

    @Test
    fun getHome() {
        val endpoint = "GET /homes/{homeId}"
        val home = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getHome(HOME_ID)
        }
        verifyHome(home, endpoint)
    }

    @Test
    fun getAirComfort() {
        val endpoint = "GET /homes/{homeId}/airComfort"
        val airComfort = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getAirComfort(HOME_ID)
        }
        verifyAirComfort(airComfort, endpoint)
    }

    @Test
    fun getHeatingSystem() {
        val endpoint = "GET /homes/{homeId}/heatingSystem"
        val heatingSystem = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getHeatingSystem(HOME_ID)
        }
        verifyHeatingSystem(heatingSystem, endpoint)
    }

    @Test
    fun getWeather() {
        val endpoint = "GET /homes/{homeId}/weather"
        val weather = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getWeather(HOME_ID)
        }
        verifyWeather(weather, endpoint)
    }

}