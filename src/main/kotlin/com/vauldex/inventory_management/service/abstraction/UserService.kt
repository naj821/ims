package com.vauldex.inventory_management.service.abstraction

import com.vauldex.inventory_management.domain.dto.request.TokenRequest
import com.vauldex.inventory_management.domain.dto.request.UserCreateRequest
import com.vauldex.inventory_management.domain.dto.request.UserLoginRequest
import com.vauldex.inventory_management.domain.dto.response.LoginResponse
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import java.util.UUID

interface UserService {
    fun authenticate(user: UserLoginRequest): LoginResponse
    fun create(user: UserCreateRequest): UserResponse
    fun find(idUser: UUID): UserResponse
    fun logout(token: String): Unit
}