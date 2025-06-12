package com.vauldex.inventory_management.repository

import com.vauldex.inventory_management.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID> {

}