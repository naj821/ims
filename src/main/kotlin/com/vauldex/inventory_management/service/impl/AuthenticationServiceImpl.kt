package com.vauldex.inventory_management.service.impl

import com.vauldex.inventory_management.domain.dto.request.TokenRequest
import com.vauldex.inventory_management.domain.entity.TokenEntity
import com.vauldex.inventory_management.repository.TokenRepository
import com.vauldex.inventory_management.service.abstraction.AuthenticationService
import com.vauldex.inventory_management.utility.JwtUtils
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
        private val jwtUtils: JwtUtils,
        private val tokenRepo: TokenRepository
): AuthenticationService {
    override fun saveTokens(token: TokenEntity): Unit {
        val doesExist = tokenRepo.existsByHashedAccessTokenAndHashedRefreshToken(
                hashedAccessToken = token.hashedAccessToken,
                hashedRefreshToken = token.hashedRefreshToken
        )

        if(doesExist) throw IllegalArgumentException("Token already exists.")

        tokenRepo.saveAndFlush(token)
        return
    }

    override fun validateAccessToken(token: String): Unit {
        val tokenMatches = jwtUtils.validateAccessToken(token)
        if(!tokenMatches) throw IllegalArgumentException("Invalid token.")
    }

    override fun validateRefreshToken(token: String): Boolean {
        val tokenMatches = jwtUtils.validateRefreshToken(token)
        if(!tokenMatches) throw IllegalArgumentException("Invalid token.")

        return true
    }

    override fun refresh(tokenRequest: TokenRequest): TokenEntity {
        try {
            val doesExists = tokenRepo.existsByHashedRefreshToken(tokenRequest.hashedRefreshToken)
            if(!doesExists) throw IllegalArgumentException("Invalid token.")

            val validAccessToken = jwtUtils.validateAccessToken(tokenRequest.hashedAccessToken)

            val response = tokenRepo.findByHashedRefreshToken(tokenRequest.hashedRefreshToken)
            if(validAccessToken) {
                val tokenEntity = TokenEntity(
                        id = response.id,
                        userId = tokenRequest.id,
                        hashedAccessToken = tokenRequest.hashedAccessToken,
                        hashedRefreshToken = tokenRequest.hashedRefreshToken,
                        createdAt = response.createdAt)
                return tokenEntity
            }

            val validToken = jwtUtils.validateRefreshToken(tokenRequest.hashedRefreshToken)
            if(!validToken) throw IllegalArgumentException("Invalid token.")



            val newAccessToken = jwtUtils.generateAccessToken(response.userId.toString())

            val tokenEntity = TokenEntity(
                    id = response.id,
                    userId = response.userId,
                    hashedAccessToken = newAccessToken,
                    hashedRefreshToken = response.hashedRefreshToken,
                    createdAt = response.createdAt
            )

            saveTokens(tokenEntity)

            return tokenEntity
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(e.message)
        }
    }
}