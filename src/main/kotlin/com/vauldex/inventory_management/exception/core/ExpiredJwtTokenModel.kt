package com.vauldex.inventory_management.exception.core

import org.springframework.http.HttpStatus

class ExpiredJwtTokenModel(var code: String?, var status: HttpStatus)