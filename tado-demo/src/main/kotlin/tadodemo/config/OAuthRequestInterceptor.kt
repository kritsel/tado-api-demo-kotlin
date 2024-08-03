package tadodemo.restconfig

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.*


@Component
open class OAuthRequestInterceptor(
    @Value("\${tado.username:undefined}")
    private val tadoUsername:String,

    @Value("\${tado.password:undefined}")
    private val tadoPassword:String,

    @Value("\${useWindowsKeystore:false}")
    private val useWindowsKeystore:Boolean,

    // Inject the OAuth authorized client service and authorized client manager
    // from the OAuthClientConfiguration class
    val authorizedClientServiceAndManager: AuthorizedClientServiceOAuth2AuthorizedClientManager
    ) : ClientHttpRequestInterceptor {

    init {
        if (useWindowsKeystore) {
            System.setProperty("javax.net.ssl.keyStore", "NONE");
            System.setProperty("javax.net.ssl.keyStoreType", "Windows-my");
            System.setProperty("javax.net.ssl.trustStore", "NONE");
            System.setProperty("javax.net.ssl.trustStoreType", "Windows-ROOT");
        }
    }

    @Throws(IOException::class)
    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {

        // Build an OAuth2 request for the tado oauth2 provider
        val authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("tado")
            .principal("anonymous")
            .build()

        // Perform the actual authorization request using the authorized client service and authorized client
        // manager. This is where the token is retrieved from the oauth2 server.
        val authorizedClient = authorizedClientServiceAndManager.authorize(authorizeRequest)

        // Get the token from the authorized client object
        val token = Objects.requireNonNull(authorizedClient).accessToken.tokenValue

        request.getHeaders().setBearerAuth(token)

//        System.out.println("RestClientInterceptor.intercept added accessToken ${token.substring(0, 15)}...")
        return execution.execute(request, body)
    }
}