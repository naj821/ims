package com.vauldex.inventory_management.exception.core

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleInvalidCredentials(ex: IllegalArgumentException): ResponseEntity<GlobalErrorMessageModel> {
        val errorMsg = GlobalErrorMessageModel(
                status = HttpStatus.BAD_REQUEST.value(),
                message = ex.message
        )
        return ResponseEntity(errorMsg, HttpStatus.BAD_REQUEST)
    }
}