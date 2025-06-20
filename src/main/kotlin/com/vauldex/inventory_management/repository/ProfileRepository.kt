package com.vauldex.inventory_management.repository

import com.vauldex.inventory_management.domain.entity.ProfileEntity
import com.vauldex.inventory_management.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProfileRepository : JpaRepository<ProfileEntity, UUID> {
    fun findByUserEntity(userEntity: UserEntity): ProfileEntity?
}