package com.vauldex.inventory_management.domain.dto.request

import com.vauldex.inventory_management.domain.entity.ProfileEntity
import com.vauldex.inventory_management.domain.entity.UserEntity

data class ProfileUpdateRequest(
    val firstName: String,
    val middleName: String,
    val lastName: String
) {
    fun toEntity(userEntity: UserEntity): ProfileEntity = ProfileEntity(
        userEntity = userEntity,
        firstName = firstName,
        middleName = middleName,
        lastName = lastName
    )
}
