package com.vauldex.inventory_management.service.abstraction

import com.vauldex.inventory_management.domain.dto.request.TokenRequest
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.domain.entity.TokenEntity
import java.util.UUID

interface AuthenticationService {
    fun saveTokens(token: TokenEntity): Unit
    fun validateAccessToken(token: String): Unit
    fun validateRefreshToken(token: String): Boolean = false
    fun refresh(jwt: String): String
}