package com.vauldex.inventory_management.service.abstraction

import com.vauldex.inventory_management.domain.dto.request.UserResquest
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.domain.entity.UserEntity

interface UserService {
    fun verifyPassword(user: UserEntity): UserResponse
}