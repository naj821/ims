package com.vauldex.inventory_management.repository

import com.vauldex.inventory_management.domain.entity.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TokenRepository: JpaRepository<TokenEntity, String>{
    fun existsByHashedAccessTokenAndHashedRefreshToken(hashedAccessToken: String, hashedRefreshToken: String): Boolean
    fun existsByHashedRefreshToken(token: String): Boolean
    fun deleteByHashedAccessToken(token: String): String
    fun deleteByHashedRefreshToken(token: String): String
}