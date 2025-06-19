package com.vauldex.inventory_management.response

import org.springframework.http.HttpStatus

data class ResponseError(
    val code: String?,
    val status: HttpStatus
)
