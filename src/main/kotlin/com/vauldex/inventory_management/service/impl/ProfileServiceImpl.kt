package com.vauldex.inventory_management.service.impl

import com.vauldex.inventory_management.domain.entity.ProfileEntity
import com.vauldex.inventory_management.repository.ProfileRepository
import com.vauldex.inventory_management.service.abstraction.ProfileService
import org.springframework.stereotype.Service

@Service
class ProfileServiceImpl(private val profileRepo: ProfileRepository):ProfileService {

    override fun save(profileEntity: ProfileEntity): Unit {
        try {
            val existingProfile = profileRepo.findByUserEntity(profileEntity.userEntity)
            val profile = existingProfile?.apply {
                firstName = profileEntity.firstName
                middleName = profileEntity.middleName
                lastName = profileEntity.lastName
            } ?: profileEntity

            profileRepo.saveAndFlush(profile)

            } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(e.message)
        }
    }
}