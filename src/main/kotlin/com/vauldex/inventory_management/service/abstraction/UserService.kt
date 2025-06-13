package com.vauldex.inventory_management.service.abstraction

import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.domain.entity.UserEntity

interface UserService {
    fun authenticate(user: UserEntity): UserResponse
}