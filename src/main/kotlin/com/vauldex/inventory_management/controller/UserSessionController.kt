package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.UserLoginRequest
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.response.ResponseSuccess
import com.vauldex.inventory_management.service.abstraction.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sessions")
class UserSessionController(private val userService: UserService) {
    @PostMapping
    fun create(@RequestBody userLogin: UserLoginRequest): ResponseSuccess<UserResponse> {
        val userResponse = userService.authenticate(userLogin)
        return ResponseSuccess(
            code = "USER_LOGIN",
            status = HttpStatus.CREATED,
            data = userResponse
        )
    }
}