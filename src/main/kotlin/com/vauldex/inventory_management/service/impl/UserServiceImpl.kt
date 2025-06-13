package com.vauldex.inventory_management.service.impl

import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.domain.entity.UserEntity
import com.vauldex.inventory_management.repository.UserRepository
import com.vauldex.inventory_management.service.abstraction.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepo: UserRepository): UserService {
    override fun authenticate(user: UserEntity): UserResponse {
        val doesExists = userRepo.existsByEmailAndPassword(email = user.email, password = user.password)

        if(!doesExists) throw IllegalArgumentException("Invalid Credentials")

        val user = userRepo.findByEmailAndPassword(email = user.email, password = user.password)
        return user.toResponse()
    }
}