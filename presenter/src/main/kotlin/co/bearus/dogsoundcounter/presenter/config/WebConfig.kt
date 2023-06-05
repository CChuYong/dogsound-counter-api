package co.bearus.dogsoundcounter.presenter.config

import co.bearus.dogsoundcounter.presenter.AuthUserArgumentResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer

@Configuration
@EnableWebFluxSecurity
class WebConfig(
    private val resolver: AuthUserArgumentResolver,
    private val authManager: APIAuthenticationManager,
    private val tokenValidator: TokenValidator,
): WebFluxConfigurer {
    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(resolver)
    }

    @Bean
    fun authenticationWebFilter() = AuthenticationWebFilter(authManager).apply {
        setServerAuthenticationConverter(tokenValidator)
    }

    @Bean
    fun springSecurityFilter(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf().disable()
            .authorizeExchange()
            .pathMatchers("/app/users/oauth/**").permitAll()
            .pathMatchers("/actuator/**").permitAll()
            .anyExchange().authenticated()
            .and()
            .addFilterAfter(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .build()
    }
}