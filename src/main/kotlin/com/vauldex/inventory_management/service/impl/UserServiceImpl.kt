package com.vauldex.inventory_management.service.impl

import com.vauldex.inventory_management.domain.dto.request.TokenRequest
import com.vauldex.inventory_management.domain.dto.request.UserCreateRequest
import com.vauldex.inventory_management.domain.dto.request.UserLoginRequest
import com.vauldex.inventory_management.domain.dto.response.LoginResponse
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.domain.entity.UserEntity
import com.vauldex.inventory_management.repository.TokenRepository
import com.vauldex.inventory_management.repository.UserRepository
import com.vauldex.inventory_management.service.abstraction.AuthenticationService
import com.vauldex.inventory_management.service.abstraction.UserService
import com.vauldex.inventory_management.utility.HashEncoder
import com.vauldex.inventory_management.utility.JwtUtils
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.bind.MissingRequestCookieException
import java.util.UUID

@Service
class UserServiceImpl(
        private val userRepo: UserRepository,
        private val hash: HashEncoder,
        private val jwtUtils: JwtUtils,
        private val tokenRepository: TokenRepository,
        private val authenticationService: AuthenticationService
): UserService {
    override fun authenticate(user: UserLoginRequest): LoginResponse {
        try {
            val doesExists = userRepo.existsByEmail(email = user.email)
            if(!doesExists) throw IllegalArgumentException("Invalid Credentials.")

            val storedUser = userRepo.findByEmail(email = user.email)

            val passwordMatches = hash.decode(user.password, storedUser.password)
            if(!passwordMatches) throw IllegalArgumentException("Invalid Credentials.")

            val accessToken = jwtUtils.generateAccessToken(userId = storedUser.id.toString())
            val refreshToken = jwtUtils.generateRefreshToken(userId = storedUser.id.toString())

            val authTokenRequest = TokenRequest(
                    id = storedUser.id!!,
                    hashedAccessToken = accessToken,
                    hashedRefreshToken = refreshToken
            )
            authenticationService.saveTokens(authTokenRequest.toEntity())
            val userResponse = storedUser.toResponse()

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
        } catch (error: Exception) {
            throw Exception(error.message)
        }
    }

    override fun find(idUser: UUID): UserResponse {
        try {
            val doesUserExist = userRepo.findById(idUser).orElse(null)

            if(doesUserExist == null) throw IllegalArgumentException("User not exist")

            return doesUserExist.toResponse()
        } catch (error: IllegalArgumentException) {
            throw IllegalArgumentException(error.message)
        }
    }

    @Transactional
     override fun logout(token: String): Unit {
        try {
            val validToken = jwtUtils.validateAccessToken(token)
            if(!validToken) throw IllegalArgumentException("Invalid token.")

            val id = jwtUtils.getUserIdFromToken(token)
            val userId = UUID.fromString(id)

           tokenRepository.deleteByUserId(userId)
        }catch (error: MissingRequestCookieException) {
            throw error
        }
    }

    override fun getUserEntity(id: UUID): UserEntity {
        val user = userRepo.findById(id).orElse(null)
        if(user == null) throw IllegalArgumentException("USER_NOT_FOUND")

        return user
    }
}