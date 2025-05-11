package jh.post.config

import org.apache.http.HttpHeaders
import org.apache.http.HttpHost
import org.apache.http.HttpResponseInterceptor
import org.apache.http.client.CredentialsProvider
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.message.BasicHeader
import org.elasticsearch.client.RestClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ElasticsearchConfig(
    private val esHost: String = "127.0.0.1",
    private val esPort: Int = 9200,
) {

    @Bean
    fun restClient(): RestClient {
        val credentialsProvider: CredentialsProvider = BasicCredentialsProvider()
        return RestClient.builder(HttpHost(esHost, esPort))
            .setHttpClientConfigCallback { httpClientBuilder ->
                httpClientBuilder.disableAuthCaching()
                httpClientBuilder.setDefaultHeaders(
                    listOf(
                        BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                    )
                )
                httpClientBuilder.addInterceptorLast(HttpResponseInterceptor { response, _ ->
                    response.addHeader("X-Elastic-Product", "Elasticsearch")
                })
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
            }.build()
    }
}
