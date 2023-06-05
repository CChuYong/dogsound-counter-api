package co.bearus.dogsoundcounter.infrastructure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import kotlin.properties.Delegates

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("app.appTokens")
class JWTTokenProperties {
    lateinit var secret: String
    var refreshTokenValidity by Delegates.notNull<Long>()
    var accessTokenValidity by Delegates.notNull<Long>()
}