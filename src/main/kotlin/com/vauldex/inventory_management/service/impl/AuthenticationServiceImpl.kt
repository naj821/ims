package com.vauldex.inventory_management.service.impl

import com.vauldex.inventory_management.domain.entity.TokenEntity
import com.vauldex.inventory_management.repository.TokenRepository
import com.vauldex.inventory_management.service.abstraction.AuthenticationService
import com.vauldex.inventory_management.utility.JwtUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

        if(doesExist) throw IllegalArgumentException("You are already logged in.")

        tokenRepo.saveAndFlush(token)
        return
    }

    override fun validateAccessToken(token: String): Unit {
        val tokenMatches = jwtUtils.validateAccessToken(token)
        if(!tokenMatches) throw IllegalArgumentException("Invalid token.")
    }

    @Transactional
    override fun refresh(jwt: String): String {
        try {
            val session = tokenRepo.existsByHashedAccessToken(jwt)
            if(!session) throw IllegalArgumentException("Invalid token.")

            val tokenData = tokenRepo.findByHashedAccessToken(token = jwt)
            val accessToken = tokenData.hashedAccessToken
            val refreshToken = tokenData.hashedRefreshToken

            val validAccessToken = jwtUtils.validateAccessToken(accessToken)
            if(validAccessToken) return accessToken

            val validRefreshToken = jwtUtils.validateRefreshToken(refreshToken)
            if(!validRefreshToken) throw IllegalArgumentException("You are not logged in.")

            val newAccessToken = jwtUtils.generateAccessToken(tokenData.userId.toString())
            val tokenEntity = TokenEntity(
                        id = tokenData.id,
                        userId = tokenData.userId,
                        hashedAccessToken = newAccessToken,
                        hashedRefreshToken = refreshToken,
                        createdAt = tokenData.createdAt
                )
            saveTokens(tokenEntity)

            return newAccessToken
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(e.message)
        }
    }
}