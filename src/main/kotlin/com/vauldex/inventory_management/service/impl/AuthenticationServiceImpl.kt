package com.vauldex.inventory_management.service.impl

import com.vauldex.inventory_management.domain.entity.TokenEntity
import com.vauldex.inventory_management.repository.TokenRepository
import com.vauldex.inventory_management.service.abstraction.AuthenticationService
import com.vauldex.inventory_management.utility.JwtUtils
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(private val jwtUtils: JwtUtils, private val tokenRepo: TokenRepository): AuthenticationService {
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

    override fun validateRefreshToken(token: String): Unit {
        val tokenMatches = jwtUtils.validateRefreshToken(token)
        if(!tokenMatches) throw IllegalArgumentException("Invalid token.")
    }
}