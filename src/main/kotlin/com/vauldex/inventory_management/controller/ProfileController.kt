package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.ProfileRequest
import com.vauldex.inventory_management.response.ResponseSuccess
import com.vauldex.inventory_management.service.abstraction.ProfileService
import com.vauldex.inventory_management.service.abstraction.UserService
import com.vauldex.inventory_management.utility.JwtUtils
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/profiles")
class ProfileController(
        private val profileService: ProfileService,
        private val userService: UserService,
        private val jwtUtils: JwtUtils
        ) {

    @PostMapping
    fun create(
            @RequestBody profile: ProfileRequest,
            @CookieValue("jwt") jwt: String
    ): ResponseSuccess<Unit> {

        val stringId = jwtUtils.getUserIdFromToken(jwt)
        val userId = UUID.fromString(stringId)

        val user = userService.getUserEntity(userId)
        val data = profileService.save(profile.toEntity(userEntity = user))

        return ResponseSuccess(
                code = "PROFILE_SAVED",
                status = HttpStatus.CREATED,
                data = data
        )
    }
}