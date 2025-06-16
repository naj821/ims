package com.vauldex.inventory_management.service.abstraction

import com.vauldex.inventory_management.domain.dto.request.UserCreateRequest
import com.vauldex.inventory_management.domain.dto.request.UserLoginRequest
import com.vauldex.inventory_management.domain.dto.response.UserResponse

interface UserService {
    fun authenticate(user: UserLoginRequest): UserResponse
    fun create(user: UserCreateRequest): UserResponse
}