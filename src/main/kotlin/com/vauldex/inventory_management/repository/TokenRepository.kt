package com.vauldex.inventory_management.repository

import com.vauldex.inventory_management.domain.dto.response.RefreshTokenResponse
import com.vauldex.inventory_management.domain.entity.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TokenRepository: JpaRepository<TokenEntity, String>{
    fun existsByHashedAccessTokenAndHashedRefreshToken(hashedAccessToken: String, hashedRefreshToken: String): Boolean
    fun existsByHashedRefreshToken(refreshToken: String): Boolean
    fun existsByHashedAccessToken(refreshToken: String): Boolean
    fun findByHashedRefreshToken(accessToken: String): TokenEntity
    fun existsByUserId(userId: UUID): Boolean
    fun findByHashedAccessToken(token: String): TokenEntity
    fun deleteByUserId(userId: UUID): String
}