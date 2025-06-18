package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.UserLoginRequest
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.response.ResponseSuccess
import com.vauldex.inventory_management.service.abstraction.AuthenticationService
import com.vauldex.inventory_management.service.abstraction.UserService
import com.vauldex.inventory_management.utility.JwtUtils
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.DeleteMapping
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
        val accessToken = userResponse.authorization.hashedAccessToken

        val cookie = Cookie("jwt", accessToken)
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        return ResponseSuccess(
            code = "USER_LOGIN",
            status = HttpStatus.CREATED,
            data = userResponse.userResponse
        )
    }

    @PostMapping("/refresh")
    fun refresh(@CookieValue("jwt") jwt: String, response: HttpServletResponse) : ResponseSuccess<Unit> {
        val data = authService.refresh(jwt)

        val cookie = Cookie("jwt", data)
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        val response = ResponseSuccess(
                code = "REFRESH_TOKEN",
                status = HttpStatus.CREATED,
                data = Unit
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

    @DeleteMapping
    fun delete(@CookieValue("jwt") jwt: String): ResponseSuccess<Unit> {
        val data = userService.logout(jwt)

        return ResponseSuccess(
                code = "USER_LOGOUT",
                status = HttpStatus.OK,
                data = data
        )
    }

}