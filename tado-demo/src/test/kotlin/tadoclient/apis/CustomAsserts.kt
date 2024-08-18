package tadoclient.apis

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestClientResponseException
import kotlin.test.junit5.JUnit5Asserter.fail

// assert that the REST API invocation does not return a response with the given httpStatus
inline fun <R> assertNoHttpErrorStatus(failMessage:String, httpStatus:HttpStatus, executable: () -> R): R {
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

// assert that the REST API invocation returns an error response with the given http status
inline fun <R> assertHttpErrorStatus(failMessage:String, httpStatus:HttpStatus, executable: () -> R): R? {
    try {
        return executable.invoke();
    } catch (e:Exception) {
        if (e is RestClientResponseException && e.statusCode==httpStatus) {
            return null
        } else {
            fail(failMessage);
        }
    }
}

// assert that the response includes an unknown property, resulting in an exception
// (used for chaos monkey testing, where an arbitrary property is ingested into the response body before deserializing;
//  chaos monkey testing is done to verify that message deserialization is set-up in a strict way,
//  so that the tests fail on (and thus reveal) any response containing new properties
//  not yet known by the generated API client code.)
inline fun <R> assertNoUnknownPropertyInResponse(executable: () -> R): R? {
    //org.springframework.web.client.RestClientException
    // org.springframework.http.converter.HttpMessageNotReadableException
    // com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
    try {
        return executable.invoke();
    } catch (e:RestClientException) {
        if (e.cause != null && e.cause is HttpMessageNotReadableException) {
            if (e.cause!!.cause != null && e.cause!!.cause is UnrecognizedPropertyException) {
                return null
            }
        }
    }
    fail ("expected an UnrecognizedPropertyException to be thrown")
}

// assert that the response does not contain any unknown properties
inline fun <R> assertUnknownPropertyErrorNotThrown(executable: () -> R): R {
    // org.springframework.web.client.RestClientException, caused by ->
    //   org.springframework.http.converter.HttpMessageNotReadableException, caused by ->
    //     com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
    try {
        return executable.invoke();
    } catch (e:RestClientException) {
        if (e.cause != null && e.cause is HttpMessageNotReadableException) {
            if (e.cause!!.cause != null && e.cause!!.cause is UnrecognizedPropertyException) {
                fail (e.cause!!.cause!!.toString())
            }
        }
        throw e
    }
}

fun failMessage403(endpoint: String) : String {
    return "403 Forbidden for $endpoint, maybe tado no longer supports this endpoint"
}

fun failMessage404(endpoint: String) : String {
    return "404 NotFound expected for $endpoint"
}