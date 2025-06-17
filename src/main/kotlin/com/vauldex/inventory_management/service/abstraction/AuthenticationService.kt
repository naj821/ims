package com.vauldex.inventory_management.service.abstraction

import com.vauldex.inventory_management.domain.entity.TokenEntity
import org.springframework.stereotype.Service

interface AuthenticationService {
    fun saveTokens(token: TokenEntity): Unit
    fun validateAccessToken(token: String): Unit
    fun validateRefreshToken(token: String): Unit
}