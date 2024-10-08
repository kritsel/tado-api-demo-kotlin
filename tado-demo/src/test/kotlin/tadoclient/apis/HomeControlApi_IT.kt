package tadoclient.apis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestClient
import tadoclient.apis.verify.verifyHomeState
import tadodemo.Application
import kotlin.test.Test
import kotlin.test.assertNotNull

@SpringBootTest(classes = arrayOf( Application::class))
@ActiveProfiles("test")
class HomeControlApi_IT(
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
) {
    val tadoHomeControlAPI = HomeControlApi(tadoRestClient)

    @Test
    fun getHomeState() {
        val endpoint = "GET /homes/{homeId}"
        val homeState = assertNoHttpErrorStatus(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoHomeControlAPI.getHomeState(HOME_ID)
        }
        assertNotNull(homeState)
        verifyHomeState(homeState, endpoint)
    }
}