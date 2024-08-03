package tadodemo.restconfig

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestClient


@Configuration
open class RestClientConfig(val oauthRequestInterceptor: OAuthRequestInterceptor) {
    @Bean("tadoRestClient")
    open fun tadoClient(): RestClient {
        return RestClient
            .builder()
            .baseUrl("https://my.tado.com/api/v2/")
            .messageConverters { it.add(MappingJackson2HttpMessageConverter()) }
            .requestInterceptor(oauthRequestInterceptor).build()
    }

//    @Bean("oauthRestClient")
//    open fun oauthClient(): RestClient {
//        return RestClient
//            .builder()
//            .baseUrl("https://auth.tado.com/oauth/token")
//            .messageConverters { it.add(MappingJackson2HttpMessageConverter()) }
//            .build()
//    }
}