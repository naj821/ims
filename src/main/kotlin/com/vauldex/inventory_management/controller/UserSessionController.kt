package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.UserLoginRequest
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.response.ResponseSuccess
import com.vauldex.inventory_management.service.abstraction.AuthenticationService
import com.vauldex.inventory_management.service.abstraction.UserService
import com.vauldex.inventory_management.utility.JwtUtils
import com.vauldex.inventory_management.utility.CookieUtils
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
        private val cookieUtils: CookieUtils,
        private val authService: AuthenticationService
) {
    @PostMapping
    fun create(@RequestBody userLogin: UserLoginRequest, response: HttpServletResponse): ResponseSuccess<UserResponse> {
        val userResponse = userService.authenticate(userLogin)
        val accessToken = userResponse.authorization.hashedAccessToken

        val cookie = cookieUtils.createJwtCookie(accessToken)
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

        val cookie = cookieUtils.createJwtCookie(data)
        response.addCookie(cookie)

        return ResponseSuccess(
            code = "REFRESH_TOKEN",
            status = HttpStatus.CREATED,
            data = Unit
        )
    }

    @GetMapping
    fun find(@CookieValue("jwt") jwt: String?): ResponseSuccess<UserResponse> {
        if(jwt == null) throw IllegalArgumentException("Invalid token.1")

        authService.validateAccessToken(jwt)

        val userId = jwtUtils.getUserIdFromToken(jwt)
        val userResponse = userService.find(UUID.fromString(userId))

        return ResponseSuccess(
            code = "USER_SESSION_FOUND",
            status = HttpStatus.OK,
            data = userResponse
        )
    }

    @DeleteMapping
    fun delete(@CookieValue("jwt") jwt: String, response: HttpServletResponse): ResponseSuccess<Unit> {
        val cookie = cookieUtils.createExpiredJwtCookie()
        response.addCookie(cookie)

        val data = userService.logout(jwt)

        return ResponseSuccess(
            code = "USER_LOGOUT",
            status = HttpStatus.OK,
            data = data
        )
    }

}
