package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.UserResquest
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.service.abstraction.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sessions")
class AuthController(private  val userService: UserService) {

    @PostMapping
    fun create(user: UserResquest): ResponseEntity<UserResponse> {
       val user =  userService.verifyPassword(user.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }
}