package com.vauldex.inventory_management.repository

import com.vauldex.inventory_management.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository: JpaRepository<UserEntity, UUID> {
    fun findByEmail(email: String): UserEntity
    fun existsByEmail(email: String): Boolean
}