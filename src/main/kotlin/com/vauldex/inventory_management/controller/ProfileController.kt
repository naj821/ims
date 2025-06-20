package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.ProfileCreateRequest
import com.vauldex.inventory_management.domain.dto.request.ProfileUpdateRequest
import com.vauldex.inventory_management.domain.dto.response.ProfileReponse
import com.vauldex.inventory_management.domain.dto.response.ProfileUpdateResponse
import com.vauldex.inventory_management.service.abstraction.ProfileService
import com.vauldex.inventory_management.response.ResponseSuccess
import com.vauldex.inventory_management.service.abstraction.AuthenticationService
import com.vauldex.inventory_management.utility.JwtUtils
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/profiles")
class ProfileController(
    private val profileService: ProfileService,
    private val authService: AuthenticationService,
    private val jwtUtils: JwtUtils,
) {

    @PostMapping
    fun create(@CookieValue("jwt") jwt: String, @RequestBody profile: ProfileCreateRequest): ResponseSuccess<ProfileReponse> {
        authService.validateAccessToken(jwt)
        val userId = jwtUtils.getUserIdFromToken(jwt)
        val profileCreate = profileService.create(UUID.fromString(userId), profile)
        return ResponseSuccess(
            code = "PROFILE_CREATED",
            status = HttpStatus.CREATED,
            data = profileCreate
        )
    }

    @PutMapping
    fun update(@CookieValue("jwt") jwt: String, @RequestBody profile: ProfileUpdateRequest): ResponseSuccess<ProfileUpdateResponse> {
        authService.validateAccessToken(jwt)
        val userId = jwtUtils.getUserIdFromToken(jwt)
        val profileUpdate = profileService.update(UUID.fromString(userId), profile)
        return ResponseSuccess(
            code = "PROFILE_UPDATED",
            status = HttpStatus.OK,
            data = profileUpdate
        )
    }

    @GetMapping
    fun update(@CookieValue("jwt") jwt: String): ResponseSuccess<ProfileReponse> {
        authService.validateAccessToken(jwt)
        val userId = jwtUtils.getUserIdFromToken(jwt)
        val profile = profileService.find(UUID.fromString(userId))
        return ResponseSuccess(
            code = "PROFILE_FOUND",
            status = HttpStatus.OK,
            data = profile
        )
    }
    
}