package tadoclient.apis

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestClient
import tadodemo.Application
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

@SpringBootTest(classes = arrayOf( Application::class))
class HomeAPI_IT(
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
) {
    val tadoHomeAPI = HomeApi(tadoRestClient)

    companion object {
        val HOME_ID:Long = 1118186
    }



    // 403
    @Test
    fun getHomes() {
        val homes = assertHttpErrorIsNotThrown(failMessage403("GET", "/homes"), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getHomes()
        }
        assertNotNull(homes)
        assertNotEquals(0, homes.size)
        assertNotNull(homes[0])
        verifyHome(homes[0])
    }

    @Test
    fun getHome() {
        val home = assertHttpErrorIsNotThrown(failMessage403("GET", "/homes/{homeId}"), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getHome(HOME_ID)
        }
        assertNotNull(home)
        verifyHome(home)
    }

    @Test
    fun getAirComfort() {
        val airComfort = assertHttpErrorIsNotThrown(failMessage403("GET", "/homes/{homeId}/airComfort"), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getAirComfort(HOME_ID)
        }
        assertNotNull(airComfort)
    }

    @Test
    fun getHeatingSystem() {
        val heatingSystem = assertHttpErrorIsNotThrown(failMessage403("GET", "/homes/{homeId}/heatingSystem"), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getHeatingSystem(HOME_ID)
        }
        assertNotNull(heatingSystem)
    }

    @Test
    fun getHomeState() {
        val homeState = assertHttpErrorIsNotThrown(failMessage403("GET", "/homes/{homeId}/state"), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getHomeState(HOME_ID)
        }
        assertNotNull(homeState)
        verifyHomeState(homeState)
    }

    @Test
    fun getWeather() {
        val weather = assertHttpErrorIsNotThrown(failMessage403("GET", "/homes/{homeId}/weather"), HttpStatus.FORBIDDEN) {
            tadoHomeAPI.getWeather(HOME_ID)
        }
        assertNotNull(weather)
    }

}