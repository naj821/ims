package com.vauldex.inventory_management.response

import org.springframework.http.HttpStatus

data class ResponseSuccess<T> (
        val code: String,
        val status: HttpStatus,
        val data: T?
)