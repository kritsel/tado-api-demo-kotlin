package tadodemo.config


import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestClient
import tadodemo.restconfig.ChaosMonkeyResponseInterceptor
import tadodemo.restconfig.OAuthRequestInterceptor


@Configuration
open class RestClientConfig(
    val oauthRequestInterceptor: OAuthRequestInterceptor,
    val monkeyTestResponseInterceptor: ChaosMonkeyResponseInterceptor
) {
    @Bean("tadoRestClient")
    open fun tadoClient(): RestClient {
        return getRestClient()
    }

    @Bean("tadoChaosMonkeyClient")
    open fun tadoChaosMonkeyClient2(): RestClient {
        return getRestClient()
            .mutate()
            .requestInterceptor(monkeyTestResponseInterceptor)
            .build()
    }

    private fun getRestClient(): RestClient {
        return RestClient
            .builder()
            .baseUrl("https://my.tado.com/api/v2/")
            .requestInterceptor(oauthRequestInterceptor)
            .build()
    }

}

