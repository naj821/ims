package com.vauldex.inventory_management.service.impl

import com.vauldex.inventory_management.domain.dto.request.UserCreateRequest
import com.vauldex.inventory_management.domain.dto.request.UserLoginRequest
import com.vauldex.inventory_management.domain.dto.request.UserResquest
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.domain.entity.UserEntity
import com.vauldex.inventory_management.repository.UserRepository
import com.vauldex.inventory_management.service.abstraction.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepo: UserRepository): UserService {
    override fun authenticate(user: UserLoginRequest): UserResponse {
        val doesExists = userRepo.existsByEmailAndPassword(email = user.email, password = user.password)

        if(!doesExists) throw IllegalArgumentException("Invalid Credentials")

        val userResponse = userRepo.findByEmailAndPassword(email = user.email, password = user.password)
        return userResponse.toResponse()
    }

    override fun create(user: UserCreateRequest): UserResponse {
        val doesUserExist = userRepo.existsByEmail(email = user.email)

        if(doesUserExist) throw IllegalArgumentException("User already exist")

        val userResponse = userRepo.save(user.toEntity())
        return userResponse.toResponse()
    }
}