package co.bearus.dogsoundcounter.presenter.config

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class APIAuthenticationManager : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return Mono.fromSupplier {
            if (authentication.credentials != null) {
                authentication.isAuthenticated = true
            }
            authentication
        }
    }
}