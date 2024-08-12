package tadoclient.apis

import org.springframework.http.HttpStatus
import org.springframework.web.client.RestClientResponseException
import kotlin.test.junit5.JUnit5Asserter.fail

inline fun <R> assertHttpErrorIsNotThrown(failMessage:String, httpStatus:HttpStatus, executable: () -> R): R {
    try {
        return executable.invoke();
    } catch (e:Exception) {
        if (e is RestClientResponseException && e.statusCode==httpStatus) {
            fail(failMessage);
        } else {
            throw e
        }
    }
}

fun failMessage403(endpointMethod: String, endpointUrl:String) : String {
    return "403 Forbidden for $endpointMethod $endpointUrl, maybe tado no longer supports this endpoint"
}