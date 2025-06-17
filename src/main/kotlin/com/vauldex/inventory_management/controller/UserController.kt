package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.UserCreateRequest
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.response.ResponseSuccess
import com.vauldex.inventory_management.service.abstraction.UserService
import com.vauldex.inventory_management.utility.HashEncoder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(private  val userService: UserService, private val hash: HashEncoder) {

    @PostMapping
    fun create(@RequestBody user: UserCreateRequest): ResponseSuccess<UserResponse> {
        val hashPassword = hash.encode(user.password)
        val hashUserPassword = UserCreateRequest(email = user.email, password = hashPassword)
       val userResponse =  userService.create(hashUserPassword)

        return ResponseSuccess(
            code = "USER_CREATED",
            status = HttpStatus.CREATED,
            data = userResponse
        )
    }
}