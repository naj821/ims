package com.vauldex.inventory_management.service.impl

import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.domain.entity.UserEntity
import com.vauldex.inventory_management.repository.UserRepository
import com.vauldex.inventory_management.service.abstraction.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepo: UserRepository): UserService {
    // override fun verifyPassword(user: UserEntity): UserResponse {
    //   val doesExists = userRepo.existByEmailAndPassword(email = user.email, password = user.password)
    //   if(!doesExists) throw Exception("Invalid credentials.")

    //   val foundUser = userRepo.findByEmailAndPassword(email = user.email, password = user.password)
    //   return foundUser.toResponse()
    // }

    override fun create(user: UserEntity): UserResponse {
      println(user)
      val user = userRepo.save(user)
      return user.toResponse()
    }
}
