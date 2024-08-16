package tadoclient.apis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestClient
import tadoclient.apis.verify.verifyDayReport
import tadodemo.Application
import java.time.LocalDate
import java.time.Month
import kotlin.test.Test
import kotlin.test.assertNotNull

@SpringBootTest(classes = arrayOf( Application::class))
@ActiveProfiles("test")
class ReportApi_IT (
    @Qualifier("tadoRestClient")
    val tadoRestClient: RestClient
){
    private val tadoReportAPI = ReportApi(tadoRestClient)

    @Test
    fun getDayReport() {
        val endpoint = "GET /homes/{homeId}/zones/{zoneId}/dayReport"
        val dayReport = assertHttpErrorIsNotThrown(failMessage403(endpoint), HttpStatus.FORBIDDEN) {
            tadoReportAPI.getZoneDayReport(HOME_ID, ZONE_ID, LocalDate.of(2024, Month.JANUARY, 11))
        }
        assertNotNull(dayReport)
        verifyDayReport(dayReport, endpoint)
    }
}