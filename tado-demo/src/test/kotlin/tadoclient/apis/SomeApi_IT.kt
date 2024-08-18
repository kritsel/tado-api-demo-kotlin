package tadoclient.apis


import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestClient
import tadodemo.Application
import kotlin.test.Test


@SpringBootTest(classes = arrayOf( Application::class))
@ActiveProfiles("test")
class SomeApi_IT (

    // this RestClient inserts a property named 'chaos' to the response JSON before it gets deserialized
    @Qualifier("tadoChaosMonkeyClient")
    val tadoChaosMonkeyRestClient: RestClient
){
    val tadoChaosMonkeyUserAPI = UserApi(tadoChaosMonkeyRestClient)

    @Test
    fun assertUnknownPropertyThrowsException() {
        assertNoUnknownPropertyInResponse {
            tadoChaosMonkeyUserAPI.getMe()
        }
    }
}