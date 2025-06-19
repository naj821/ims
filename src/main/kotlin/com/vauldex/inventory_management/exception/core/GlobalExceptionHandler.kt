package com.vauldex.inventory_management.exception.core

import com.vauldex.inventory_management.response.ResponseError
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingRequestCookieException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ResponseError> {
        val response = ResponseError(
            code = ex.message,
            status = HttpStatus.INTERNAL_SERVER_ERROR
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleInvalidCredentials(ex: IllegalArgumentException): ResponseEntity<GlobalErrorMessageModel> {
        val errorMsg = GlobalErrorMessageModel(
                code = ex.message,
                status = HttpStatus.BAD_REQUEST,

        )
        return ResponseEntity(errorMsg, HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler(ExpiredJwtException::class)
    fun handleExpireJwtToken(ex: ExpiredJwtException): ResponseEntity<ExpiredJwtTokenModel> {
        val errorMsg = ExpiredJwtTokenModel(
                code = ex.message,
                status = HttpStatus.BAD_REQUEST
        )
        return ResponseEntity(errorMsg, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MissingRequestCookieException::class)
    fun handleMissingRequestCookie(ex: MissingRequestCookieException): ResponseEntity<MissingRequestExceptionModel> {
        val errorMsg = MissingRequestExceptionModel(
                code = ex.message,
                status = HttpStatus.BAD_REQUEST
        )

        return ResponseEntity(errorMsg, HttpStatus.BAD_REQUEST)
    }
}
