package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.UserLoginRequest
import com.vauldex.inventory_management.domain.dto.response.LoginResponse
import com.vauldex.inventory_management.domain.dto.response.Tokens
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.response.ResponseSuccess
import com.vauldex.inventory_management.service.abstraction.UserService
import com.vauldex.inventory_management.utility.JwtUtils
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sessions")
class UserSessionController(
        private val userService: UserService,
        private val jwtUtils: JwtUtils
) {
    @PostMapping
    fun create(@RequestBody userLogin: UserLoginRequest): ResponseSuccess<LoginResponse> {
        val userResponse = userService.authenticate(userLogin)
        
        val accessToken = jwtUtils.generateAccessToken(userId = userResponse.id.toString())
        
        val refreshToken = jwtUtils.generateRefreshToken(userId = userResponse.id.toString())
        val tokens = Tokens(accessToken = accessToken, refreshToken = refreshToken)

        val loginResponse = LoginResponse(userResponse = userResponse, authorization = tokens)

        return ResponseSuccess(
            code = "USER_LOGIN",
            status = HttpStatus.CREATED,
            data = loginResponse
        )
    }
}