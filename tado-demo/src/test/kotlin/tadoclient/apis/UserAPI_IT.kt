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
class UserAPI_IT (
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
){
    companion object {
        val HOME_ID:Long = 1118186
        val ZONE_ID:Long = 4
    }

    val tadoUserAPI = UserApi(tadoRestClient)

    @Test
    fun getMe() {
        val user = assertHttpErrorIsNotThrown(failMessage403("GET", "/me"), HttpStatus.FORBIDDEN) {
            tadoUserAPI.getMe()
        }
        assertNotNull(user)
        verifyUser(user)
    }

    @Test
    fun getUsers() {
        val users = assertHttpErrorIsNotThrown(failMessage403("GET", "/homes/users"), HttpStatus.FORBIDDEN) {
            tadoUserAPI.getUsers(HOME_ID)
        }

        // check users
        assertNotNull(users)
        assertNotEquals(0, users.size)
        assertNotNull(users[0])
        verifyUser(users[0])
    }
}