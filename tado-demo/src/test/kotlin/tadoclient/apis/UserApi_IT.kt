package tadoclient.apis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestClient
import tadoclient.apis.verify.verifyUser
import tadodemo.Application
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

@SpringBootTest(classes = arrayOf( Application::class))
class UserApi_IT (
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
){
    val tadoUserAPI = UserApi(tadoRestClient)

    @Test
    fun getMe() {
        val endpoint = "GET /me"
        val user = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoUserAPI.getMe()
        }
        assertNotNull(user)
        verifyUser(user, endpoint)
    }

    @Test
    fun getUsers() {
        val endpoint = "GET /homes/{homeId}/users"
        val users = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoUserAPI.getUsers(HOME_ID)
        }

        // check users
        assertNotNull(users)
        assertNotEquals(0, users.size)
        assertNotNull(users[0])
        verifyUser(users[0], endpoint, "response[0]")
    }
}