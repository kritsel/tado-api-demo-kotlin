package tadodemo

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import tadoclient.apis.UserApi
import tadoclient.apis.ZoneApi
import java.time.LocalDateTime

@Service
class ScheduledTask(
    @Value("\${useWindowsTrustStore:false}")
    private val useWindowsTrustStore:Boolean,

    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
) {

    init {
        if (useWindowsTrustStore) {
//            System.out.println("useWindowsTrustStore")
            System.setProperty("javax.net.ssl.trustStore", "NONE");
            System.setProperty("javax.net.ssl.trustStoreType", "Windows-ROOT");
        }
    }

    val tadoUserAPI = UserApi(tadoRestClient)
    val tadoZoneAPI = ZoneApi(tadoRestClient)

    @Scheduled(cron = "0 * * * * *")
    fun collectTadoMetrics() {
        val now = LocalDateTime.now()
        val user = tadoUserAPI.getMe();
        user.homes?.forEach { home ->
            System.out.print("${now.hour}:${now.minute} ${home.name}")
            val zones = tadoZoneAPI.getZones(home.id!!)
            zones.forEach { zone ->
                val zoneState = tadoZoneAPI.getZoneState(home.id!!, zone.id!!)
                System.out.print(
                    " || ${zone.name} | " +
                            "HP: ${zoneState.activityDataPoints?.heatingPower?.percentage}%, " +
                            "T: ${zoneState.sensorDataPoints?.insideTemperature?.celsius}C, " +
                            "H: ${zoneState.sensorDataPoints?.humidity?.percentage}% ")
            }
        }
        System.out.println()
    }
}