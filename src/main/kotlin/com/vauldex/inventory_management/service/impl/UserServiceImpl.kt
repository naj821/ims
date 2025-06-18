package com.vauldex.inventory_management.service.impl

import com.vauldex.inventory_management.domain.dto.request.UserCreateRequest
import com.vauldex.inventory_management.domain.dto.request.UserLoginRequest
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.repository.UserRepository
import com.vauldex.inventory_management.service.abstraction.UserService
import com.vauldex.inventory_management.utility.HashEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserServiceImpl(private val userRepo: UserRepository, private val hash: HashEncoder): UserService {
    override fun authenticate(user: UserLoginRequest): UserResponse {
        try {
            val doesExists = userRepo.existsByEmail(email = user.email)
            if(!doesExists) throw IllegalArgumentException("Invalid Credentials.")

            val storedUser = userRepo.findByEmail(email = user.email)

            val passwordMatches = hash.decode(user.password, storedUser.password)
            if(!passwordMatches) throw IllegalArgumentException("Invalid Credentials.")

            val userResponse = userRepo.findByEmail(email = user.email)
            return userResponse.toResponse()
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

    override fun find(idUser: UUID): UserResponse {
        try {
            val doesUserExist = userRepo.findById(idUser).orElse(null)

            if(doesUserExist == null) throw IllegalArgumentException("User not exist")

            return doesUserExist.toResponse()
        } catch (error: IllegalArgumentException) {
            throw IllegalArgumentException(error.message)
        }
    }
}