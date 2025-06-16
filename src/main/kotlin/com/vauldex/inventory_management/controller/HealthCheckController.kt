package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.response.ResponseSuccess
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {
    @GetMapping("/")
    fun index(): ResponseSuccess<String> {
        return ResponseSuccess(
            code = "SERVER_RUNNING",
            status = HttpStatus.OK,
            data = ""
        )
    }
}
