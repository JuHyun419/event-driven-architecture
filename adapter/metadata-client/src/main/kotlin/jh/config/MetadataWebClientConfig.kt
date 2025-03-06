package jh.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class MetadataWebClientConfig(
    @Value("\${external-server.metadata.url}")
    val metadataApiUrl: String
) {

    @Bean
    @Primary
    fun metadataWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(metadataApiUrl)
            .build()
    }
}
