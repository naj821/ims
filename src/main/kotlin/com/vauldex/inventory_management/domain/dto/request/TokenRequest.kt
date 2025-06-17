package com.vauldex.inventory_management.domain.dto.request

import com.vauldex.inventory_management.domain.entity.TokenEntity
import java.util.UUID

data class TokenRequest(val id: UUID, val hashedAccessToken: String, val hashedRefreshToken: String)

{
    fun toEntity(): TokenEntity = TokenEntity(
            userId = id,
            hashedAccessToken = hashedAccessToken,
            hashedRefreshToken = hashedRefreshToken
    )
}
