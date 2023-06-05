package co.bearus.dogsoundcounter.infrastructure.clients

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("app")
class WebClientProperties {
    lateinit var webclients: Map<String, ClientConfig>

    fun getClientConfig(clientName: String) = webclients[clientName]
}

data class ClientConfig(
    val baseUrl: String,
    val header: Map<String, String>? = null,
)
