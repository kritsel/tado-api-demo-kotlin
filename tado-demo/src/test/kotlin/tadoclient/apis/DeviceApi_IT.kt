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
class DeviceApi_IT (
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
){
    val tadoDeviceAPI = DeviceApi(tadoRestClient)

    // TODO: all device methods
}