package com.vauldex.inventory_management.exception.core

import org.springframework.http.HttpStatus

class GlobalErrorMessageModel (
        var code: String?,
        var status: HttpStatus
)