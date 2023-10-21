package hanghae99.plus2.team3.hanghaeorder.common.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * BusinessExceptionandler
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/17/23
 */

@RestControllerAdvice
class OrderExceptionHandler(
    private val log: Logger
) {
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun defaultException(request: HttpServletRequest?, e: Exception): ResponseEntity<String> {
        log.error(e.toString())
        return ResponseEntity.internalServerError().body(e.toString())
    }
}
