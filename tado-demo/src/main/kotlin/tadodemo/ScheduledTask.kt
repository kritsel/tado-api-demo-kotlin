package tadodemo

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import tadoclient.apis.DefaultApi
import java.time.Instant
import java.time.LocalDateTime

@Service
class ScheduledTask(
    @Value("\${useWindowsKeystore:false}")
    private val useWindowsKeystore:Boolean,

    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
) {

    init {
        if (useWindowsKeystore) {
            System.setProperty("javax.net.ssl.keyStore", "NONE");
            System.setProperty("javax.net.ssl.keyStoreType", "Windows-my");
            System.setProperty("javax.net.ssl.trustStore", "NONE");
            System.setProperty("javax.net.ssl.trustStoreType", "Windows-ROOT");
        }
    }

    val tadoAPI = DefaultApi(tadoRestClient)

    @Scheduled(cron = "0 * * * * *")
    fun collectTadoMetrics() {
        val now = LocalDateTime.now()
        val user = tadoAPI.getMe();
        user.homes?.forEach { home ->
            System.out.print("${now.hour}:${now.minute} ${home.name}")
            val zones = tadoAPI.getZones(home.id!!)
            zones.forEach { zone ->
                val zoneState = tadoAPI.getZoneSate(home.id!!, zone.id!!)
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