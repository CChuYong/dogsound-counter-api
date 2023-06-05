package co.bearus.dogsoundcounter.presenter

import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider
import org.slf4j.LoggerFactory
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequestUser

@Component
class AuthUserArgumentResolver(
    private val tokenProvider: TokenProvider,
) : HandlerMethodArgumentResolver {

    private val log = LoggerFactory.getLogger(AuthUserArgumentResolver::class.java)

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(RequestUser::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): LoginUser {
        val token = webRequest.getHeader("Authorization") ?: ""
        return LoginUser(
            userId = tokenProvider.extractUserIdFromToken(token)
        )
    }
}

data class LoginUser(
    val userId: String,
)