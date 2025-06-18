package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.TokenRequest
import com.vauldex.inventory_management.domain.dto.request.UserLoginRequest
import com.vauldex.inventory_management.domain.dto.response.LoginResponse
import com.vauldex.inventory_management.domain.entity.TokenEntity
import com.vauldex.inventory_management.response.ResponseSuccess
import com.vauldex.inventory_management.service.abstraction.AuthenticationService
import com.vauldex.inventory_management.service.abstraction.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sessions")
class UserSessionController(
        private val userService: UserService,
        private val authenticationService: AuthenticationService,
) {
    @PostMapping
    fun create(@RequestBody userLogin: UserLoginRequest): ResponseSuccess<LoginResponse> {
        val loginResponse = userService.authenticate(userLogin)

        return ResponseSuccess(
            code = "USER_LOGIN",
            status = HttpStatus.CREATED,
            data = loginResponse
        )
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody refreshTokenRequest: TokenRequest): ResponseSuccess<TokenEntity> {
        val data = authenticationService.refresh(refreshTokenRequest)

        val response = ResponseSuccess(
                code = "REFRESH_TOKEN",
                status = HttpStatus.CREATED,
                data = data
        )

        return response
    }

    @PostMapping("/logout")
    fun logout(@RequestBody tokens: TokenRequest): ResponseSuccess<String> {
        val data = userService.logout(tokens)

        val response = ResponseSuccess(
                code = "USER_LOGOUT",
                status = HttpStatus.OK,
                data = data
        )
        return response
    }

}