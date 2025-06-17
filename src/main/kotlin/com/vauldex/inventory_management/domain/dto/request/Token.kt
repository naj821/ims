package com.vauldex.inventory_management.domain.dto.request

import com.vauldex.inventory_management.domain.entity.TokenEntity

data class Token(val hashedAccessToken: String, val hashedRefreshToken: String)

{
    fun toEntity(): TokenEntity = TokenEntity(
            hashedAccessToken = hashedAccessToken,
            hashedRefreshToken = hashedRefreshToken
    )
}
