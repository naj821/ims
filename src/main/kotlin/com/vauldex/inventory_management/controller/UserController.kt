package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.UserCreateRequest
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.response.ResponseSuccess
import com.vauldex.inventory_management.service.abstraction.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(private  val userService: UserService) {

    @PostMapping
    fun create(@RequestBody user: UserCreateRequest): ResponseSuccess<UserResponse> {
       val userResponse =  userService.create(user)

        return ResponseSuccess(
            code = "USER_CREATED",
            status = HttpStatus.CREATED,
            data = userResponse
        )
    }
}