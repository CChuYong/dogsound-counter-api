package co.bearus.dogsoundcounter.presenter

import co.bearus.dogsoundcounter.entities.exception.DomainException
import co.bearus.dogsoundcounter.entities.exception.ErrorCode
import co.bearus.dogsoundcounter.presenter.dto.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception

@ControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(DomainException::class)
    fun handleDomainException(exception: DomainException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(
            ErrorResponse(
                code = exception.errorCode.code,
                message = exception.errorCode.message,
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleUnhandledException(exception: Exception): ResponseEntity<ErrorResponse> {
        exception.printStackTrace()
        return ResponseEntity.badRequest().body(
            ErrorResponse(
                code = ErrorCode.UNKNOWN_ERROR.code,
                message = ErrorCode.UNKNOWN_ERROR.message,
            )
        )
    }
}