package com.example.task_tracker.exception

import com.example.task_tracker.model.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException::class)
    fun handleNotFound(ex: ResponseStatusException): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(
            status = ex.statusCode.value(),
            error = ex.statusCode.toString(),
            message = ex.reason ?: "Error occurred"
        )
        return ResponseEntity(body, ex.statusCode)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val message = ex.bindingResult.fieldErrors
            .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
        val body = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Validation Error",
            message = message
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

}
