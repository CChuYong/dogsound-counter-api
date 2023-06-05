package co.bearus.dogsoundcounter.presenter

import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider
import org.slf4j.LoggerFactory
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.reactive.BindingContext
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequestUser

@Component
class AuthUserArgumentResolver(
    private val tokenProvider: TokenProvider,
) : org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver {

    private val log = LoggerFactory.getLogger(AuthUserArgumentResolver::class.java)

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(RequestUser::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        bindingContext: BindingContext,
        exchange: ServerWebExchange
    ): Mono<Any> {
     //   val token = webRequest.getHeader("Authorization") ?: ""
        return Mono.just(
            LoginUser(
                userId = "01H256SJWQXX11JYFNQJS2RTEZ"
            )
        )
    }
}

data class LoginUser(
    val userId: String,
)