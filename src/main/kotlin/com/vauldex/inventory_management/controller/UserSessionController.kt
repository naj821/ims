package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.Token
import com.vauldex.inventory_management.domain.dto.request.UserLoginRequest
import com.vauldex.inventory_management.domain.dto.response.LoginResponse
import com.vauldex.inventory_management.domain.entity.TokenEntity
import com.vauldex.inventory_management.response.ResponseSuccess
import com.vauldex.inventory_management.service.abstraction.AuthenticationService
import com.vauldex.inventory_management.service.abstraction.UserService
import com.vauldex.inventory_management.utility.JwtUtils
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.vauldex.inventory_management.utility.HashEncoder

@RestController
@RequestMapping("/api/sessions")
class UserSessionController(
        private val userService: UserService,
        private val jwtUtils: JwtUtils,
        private val authenticationService: AuthenticationService,
        private val hash: HashEncoder
) {
    @PostMapping
    fun create(@RequestBody userLogin: UserLoginRequest): ResponseSuccess<LoginResponse> {
        val userResponse = userService.authenticate(userLogin)
        
        val accessToken = jwtUtils.generateAccessToken(userId = userResponse.id.toString())
        
        val refreshToken = jwtUtils.generateRefreshToken(userId = userResponse.id.toString())

        val authToken = Token(
                hashedAccessToken = accessToken,
                hashedRefreshToken = refreshToken
        )

        saveToken(token = authToken.toEntity())

        val loginResponse = LoginResponse(userResponse = userResponse, authorization = authToken)

        return ResponseSuccess(
            code = "USER_LOGIN",
            status = HttpStatus.CREATED,
            data = loginResponse
        )
    }
    fun saveToken(token: TokenEntity): Unit {
        authenticationService.saveTokens(token)
    }
}