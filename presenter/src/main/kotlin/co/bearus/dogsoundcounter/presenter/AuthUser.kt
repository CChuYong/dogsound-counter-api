package co.bearus.dogsoundcounter.presenter

import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.reactive.BindingContext
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequestUser

@Component
class AuthUserArgumentResolver : org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(RequestUser::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        bindingContext: BindingContext,
        exchange: ServerWebExchange
    ): Mono<Any> {
        return Mono.just(
            LoginUser(
                userId = exchange.getAttribute<String>("userId")!!
            )
        )
    }
}

data class LoginUser(
    val userId: String,
)
