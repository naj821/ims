package com.vauldex.inventory_management.domain.dto.request

import com.vauldex.inventory_management.domain.entity.UserEntity

data class UserRequest(val email: String, val password: String)

{
    fun toEntity(): UserEntity = UserEntity(email = email, password = password)
}
