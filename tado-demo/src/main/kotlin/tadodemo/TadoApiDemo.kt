package tadodemo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestTemplate
import tadoclient.apis.DefaultApi
import java.text.SimpleDateFormat
import java.util.*



@Component
class TadoApiDemo(
    @Value("\${useWindowsKeystore:false}")
    private val useWindowsKeystore:Boolean,

    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
) {
    companion object {
        // homeID for Fregatstraat: 1118186
        const val HOME_ID = "1118186"
        const val ZONE_ID_DICK = "1"
        const val ZONE_ID_KRISTEL = "4"
    }
    val tadoAPI = DefaultApi(tadoRestClient)

    fun doIt() {
        // set system properties to instruct the JVM to use the Windows key store
        if (useWindowsKeystore) {
            System.out.println("useWindowsKeystore")
            System.setProperty("javax.net.ssl.keyStore", "NONE");
            System.setProperty("javax.net.ssl.keyStoreType", "Windows-my");
            System.setProperty("javax.net.ssl.trustStore", "NONE");
            System.setProperty("javax.net.ssl.trustStoreType", "Windows-ROOT");
        }

        val me = tadoAPI.getMe()
        System.out.println("################################################")
        System.out.println("# me")
        System.out.println(me)

        val home = tadoAPI.getHome(HOME_ID)
        System.out.println("################################################")
        System.out.println("# home")
        System.out.println(home)

        val homeState = tadoAPI.getHomeState(HOME_ID)
        System.out.println("################################################")
        System.out.println("# home state")
        System.out.println(homeState)

        val mobileDevices = tadoAPI.getMobileDevices(HOME_ID)
        System.out.println("################################################")
        System.out.println("# mobile devices")
        System.out.println(mobileDevices)

//        val singlemobileDevice = tadoAPI.getMobileDevice(HOME_ID, "8575918")
//        System.out.println("################################################")
//        System.out.println("# single mobile device")
//        System.out.println(singlemobileDevice)

        val devices = tadoAPI.getDevices(HOME_ID)
        System.out.println("################################################")
        System.out.println("# devices")
        System.out.println(devices)

        val zones = tadoAPI.getZones(HOME_ID)
        System.out.println("################################################")
        System.out.println("# zones")
        System.out.println(zones)

        val zoneState = tadoAPI.getZoneSate(HOME_ID, ZONE_ID_KRISTEL)
        System.out.println("################################################")
        System.out.println("# zone state")
        System.out.println(zoneState)
    }
}