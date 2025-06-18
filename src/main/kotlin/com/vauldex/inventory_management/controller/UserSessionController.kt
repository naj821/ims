package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.TokenRequest
import com.vauldex.inventory_management.domain.dto.request.UserLoginRequest
import com.vauldex.inventory_management.domain.dto.response.LoginResponse
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.domain.entity.ProductEntity
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
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import java.util.UUID

@RestController
@RequestMapping("/api/sessions")
class UserSessionController(
        private val userService: UserService,
        private val jwtUtils: JwtUtils,
        private val authService: AuthenticationService
) {
    @PostMapping
    fun create(@RequestBody userLogin: UserLoginRequest, response: HttpServletResponse): ResponseSuccess<UserResponse> {
        val userResponse = userService.authenticate(userLogin)
        
        val accessToken = jwtUtils.generateAccessToken(userId = userResponse.id.toString())
        
        val refreshToken = jwtUtils.generateRefreshToken(userId = userResponse.id.toString())

        val authTokenRequest = TokenRequest(
                id = userResponse.id,
                hashedAccessToken = accessToken,
                hashedRefreshToken = refreshToken
        )
        authService.saveTokens(authTokenRequest.toEntity())

        val cookie = Cookie("jwt", accessToken)
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        val loginResponse = LoginResponse(userResponse = userResponse, authorization = authTokenRequest)

        return ResponseSuccess(
            code = "USER_LOGIN",
            status = HttpStatus.CREATED,
            data = userResponse
        )
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody refreshTokenRequest: TokenRequest): ResponseSuccess<TokenEntity> {
        val data = authService.refresh(refreshTokenRequest)

        val response = ResponseSuccess(code = "REFRESH_TOKEN",
                status = HttpStatus.CREATED,
                data = data
        )

        return response
    }

    @GetMapping
    fun find(@CookieValue("jwt") jwt: String): ResponseSuccess<UserResponse> {
        authService.validateAccessToken(jwt)
      
        val userId = jwtUtils.getUserIdFromToken(jwt)
        val userResponse = userService.find(UUID.fromString(userId))

        return ResponseSuccess(
            code = "USER_FOUND",
            status = HttpStatus.OK,
            data = userResponse
        )
    }

}
