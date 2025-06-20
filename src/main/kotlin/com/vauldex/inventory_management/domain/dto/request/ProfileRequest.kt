package com.vauldex.inventory_management.domain.dto.request

import com.vauldex.inventory_management.domain.entity.ProfileEntity
import com.vauldex.inventory_management.domain.entity.UserEntity
import java.util.UUID

data class ProfileRequest(val firstName: String, val middleName: String?, val lastName: String)

{
    fun toEntity(userEntity: UserEntity): ProfileEntity = ProfileEntity(
            firstName = firstName,
            middleName = middleName,
            lastName = lastName,
            userEntity = userEntity
    )
}
