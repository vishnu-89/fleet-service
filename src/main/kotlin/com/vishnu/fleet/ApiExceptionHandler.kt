package com.vishnu.fleet

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(Throwable::class)
    fun handleError(request: HttpServletRequest, exception: Throwable): ResponseEntity<ErrorInfo> {
        val (code, message) = when (exception) {
            is MethodArgumentNotValidException -> HttpStatus.BAD_REQUEST to exception.fieldError?.defaultMessage
            is DataIntegrityViolationException -> HttpStatus.CONFLICT to exception.localizedMessage
            is NotFoundExecption -> HttpStatus.NOT_FOUND to exception.localizedMessage
            else -> throw exception
        }

        val errorInfo = ErrorInfo(error = message!!, path = request.requestURI)

        val headers = HttpHeaders()
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

        return ResponseEntity(errorInfo, headers, code)
    }
}

data class ErrorInfo(
    val error: String,
    val path: String
)

class NotFoundExecption(override val message : String) : RuntimeException()
