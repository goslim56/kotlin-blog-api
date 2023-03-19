package com.goslim.kotlinblogapi.exception

import com.goslim.kotlinblogapi.dto.ErrorResponseDTO
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.WebUtils
import javax.servlet.http.HttpServletRequest


@RestControllerAdvice
class GlobalExceptionHandler(
//    val slackService: SlackService
) {

    private val log = KotlinLogging.logger {}


    @ExceptionHandler(Throwable::class)
    fun handle(req: HttpServletRequest?, ex: Exception?): ResponseEntity<HttpStatus> {
        log.error("Throwable Error ======== " + ex!!.stackTraceToString(), ex)
        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandle(req: HttpServletRequest?, ex: Exception?): ResponseEntity<HttpStatus> {
        log.error("Runtime Exception ======== " + ex!!.stackTraceToString(), ex)
        return ResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

//    @ExceptionHandler(value = [DataNotFoundException::class, InValidRequestException::class])
//    fun dataNotFoundExceptionHandler(ex: Exception?): ResponseEntity<ErrorResponse> {
//        // TODO: log format 정의 필요
//        log.error("Data not found Exception ======== " + ex!!.stackTraceToString(), ex)
//        Sentry.captureException(ex)
//        return ResponseEntity(ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.message), HttpStatus.BAD_REQUEST)
//    }


    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(
        req: HttpServletRequest,
        ex: MethodArgumentNotValidException
    ): ResponseEntity<HttpStatus> {
        log.info("request Error ======== " + ex.stackTraceToString(), ex)
        return ResponseEntity(
//            ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.fieldError?.defaultMessage ?: e.message),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(BindException::class)
    fun validException(req: HttpServletRequest, ex: BindException): ResponseEntity<ErrorResponseDTO> {
        log.info("request Error ======== " + ex.stackTraceToString(), ex)
        return ResponseEntity(
            ErrorResponseDTO(ex.bindingResult.allErrors[0].defaultMessage),
            HttpStatus.BAD_REQUEST
        )
    }
}
