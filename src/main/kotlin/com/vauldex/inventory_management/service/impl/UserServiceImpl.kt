package com.vauldex.inventory_management.service.impl

import com.vauldex.inventory_management.domain.dto.request.TokenRequest
import com.vauldex.inventory_management.domain.dto.request.UserCreateRequest
import com.vauldex.inventory_management.domain.dto.request.UserLoginRequest
import com.vauldex.inventory_management.domain.dto.response.LoginResponse
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.repository.TokenRepository
import com.vauldex.inventory_management.repository.UserRepository
import com.vauldex.inventory_management.service.abstraction.AuthenticationService
import com.vauldex.inventory_management.service.abstraction.UserService
import com.vauldex.inventory_management.utility.HashEncoder
import com.vauldex.inventory_management.utility.JwtUtils
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
        private val userRepo: UserRepository,
        private val hash: HashEncoder,
        private  val tokenRepository: TokenRepository,
        private val jwtUtils: JwtUtils,
        private val authenticationService: AuthenticationService
): UserService {
    override fun authenticate(user: UserLoginRequest): LoginResponse {
        try {
            val doesExists = userRepo.existsByEmail(email = user.email)
            if(!doesExists) throw IllegalArgumentException("Invalid Credentials.")

            val storedUser = userRepo.findByEmail(email = user.email)

            val passwordMatches = hash.decode(user.password, storedUser.password)
            if(!passwordMatches) throw IllegalArgumentException("Invalid Credentials.")

            val userResult = userRepo.findByEmail(email = user.email)

            val accessToken = jwtUtils.generateAccessToken(userId = userResult.id.toString())
            val refreshToken = jwtUtils.generateRefreshToken(userId = userResult.id.toString())

            val authTokenRequest = TokenRequest(
                    id = userResult.id!!,
                    hashedAccessToken = accessToken,
                    hashedRefreshToken = refreshToken
            )
            authenticationService.saveTokens(authTokenRequest.toEntity())
            val userResponse = userResult.toResponse()

            val loginResponse = LoginResponse(userResponse = userResponse, authorization = authTokenRequest)

            return loginResponse
        } catch (error: IllegalArgumentException) {
            throw IllegalArgumentException(error.message)
        }
    }

    override fun create(user: UserCreateRequest): UserResponse {
        try {
            val doesUserExist = userRepo.existsByEmail(email = user.email)

            if(doesUserExist) throw IllegalArgumentException("User already exist")

            val userResponse = userRepo.save(user.toEntity())
            return userResponse.toResponse()
        } catch (error: IllegalArgumentException) {
            throw IllegalArgumentException(error.message)
        }
    }

    override fun logout(token: TokenRequest): String {
        try {
            val doesExists = tokenRepository.existsByHashedRefreshToken(token.hashedRefreshToken)
            if(!doesExists) throw IllegalArgumentException("Invalid token.")

            tokenRepository.deleteByHashedRefreshToken(token.hashedRefreshToken)
            return "Logout successfully."
        }catch (error: IllegalArgumentException) {
            throw IllegalArgumentException(error.message)
        }
    }
}