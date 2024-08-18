package tadoclient.apis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestClient
import tadoclient.apis.verify.verifyUser
import tadodemo.Application
import kotlin.test.Test
import kotlin.test.assertNotEquals

@SpringBootTest(classes = arrayOf( Application::class))
@ActiveProfiles("test")
class UserApi_IT (
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
){
    val tadoUserAPI = UserApi(tadoRestClient)

    @Test
    fun getMe() {
        val endpoint = "GET /me"
        val user = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoUserAPI.getMe()
        }
        verifyUser(user, endpoint)
    }

    @Test
    fun getUsers() {
        val endpoint = "GET /homes/{homeId}/users"
        val users = assertUnknownPropertyErrorNotThrown {
            assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
                tadoUserAPI.getUsers(HOME_ID)
            }
        }

        // check users
        assertNotEquals(0, users.size)
        users.forEachIndexed{i, elem -> verifyUser(elem, endpoint, "response[$i]")}
    }
}