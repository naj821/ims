package com.vauldex.inventory_management.repository

import com.vauldex.inventory_management.domain.entity.RefreshTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RefreshTokenRepository: JpaRepository<RefreshTokenEntity, UUID>{
//    fun findByIdAndHashToken()
}