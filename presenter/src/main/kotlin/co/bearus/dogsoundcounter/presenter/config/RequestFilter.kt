package co.bearus.dogsoundcounter.presenter.config

import org.slf4j.LoggerFactory
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class RequestFilter : WebFilter {
    private val log = LoggerFactory.getLogger(RequestFilter::class.java)
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val startTime = System.currentTimeMillis()
        if (exchange.request.path.toString().startsWith("/actuator")) return chain.filter(exchange)
        return ReactiveSecurityContextHolder
            .getContext()
            .map { exchange.attributes["userId"] = it.authentication.name }
            .then(chain.filter(exchange))
            .doFinally { _ ->
                val totalTime = System.currentTimeMillis() - startTime
                log.info(
                    "[{}] {} {} {} {} {}ms",
                    exchange.attributes["userId"],
                    exchange.request.method,
                    exchange.request.path,
                    exchange.request.queryParams.toString(),
                    exchange.response.statusCode,
                    totalTime
                )
            }
    }
}