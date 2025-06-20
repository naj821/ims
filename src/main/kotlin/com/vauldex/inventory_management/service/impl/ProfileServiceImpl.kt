package com.vauldex.inventory_management.service.impl

import com.vauldex.inventory_management.domain.dto.request.ProfileCreateRequest
import com.vauldex.inventory_management.domain.dto.request.ProfileUpdateRequest
import com.vauldex.inventory_management.domain.dto.response.ProfileReponse
import com.vauldex.inventory_management.domain.dto.response.ProfileUpdateResponse
import com.vauldex.inventory_management.domain.entity.ProfileEntity
import com.vauldex.inventory_management.service.abstraction.ProfileService
import com.vauldex.inventory_management.repository.ProfileRepository
import com.vauldex.inventory_management.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProfileServiceImpl(private val profileRepository: ProfileRepository, private val userRepository: UserRepository) : ProfileService {
    override fun create(userId: UUID, profile: ProfileCreateRequest): ProfileReponse {
        try {
            val findUser = userRepository.findById(userId).orElse(null)
            if(findUser == null) throw IllegalArgumentException("USER_NOT_FOUND")
    
            val response = profileRepository.save(profile.toEntity(findUser))
            return response.toResponse()
        } catch(ex: Exception){
            throw Exception(ex.message + "1")
        }
    }

    override fun update(userId: UUID, profile: ProfileUpdateRequest): ProfileUpdateResponse {
        try {
            val findUser = userRepository.findById(userId).orElse(null)
            if(findUser == null) throw IllegalArgumentException("USER_NOT_FOUND")

            val findProfile = profileRepository.findByUserEntity(findUser)
            if(findProfile == null) throw IllegalArgumentException("PROFILE_NOT_FOUND")

            val response = profileRepository.saveAndFlush(ProfileEntity(
                userEntity = findUser,
                id = findProfile.id,
                firstName = profile.firstName.ifBlank { findProfile.firstName },
                middleName = profile.middleName.ifBlank { findProfile.middleName },
                lastName = profile.lastName.ifBlank { findProfile.lastName },
                createdAt = findProfile.createdAt
            ))
            return ProfileUpdateResponse(response.toResponse())
        } catch(ex: Exception){
            throw Exception(ex.message + "2")
        }
    }

    override fun find(userId: UUID): ProfileReponse {
        try {
            val findUser = userRepository.findById(userId).orElse(null)
            if(findUser == null) throw IllegalArgumentException("USER_NOT_FOUND")

            val findProfile = profileRepository.findByUserEntity(findUser)
            if(findProfile == null) throw IllegalArgumentException("PROFILE_NOT_FOUND")

            return findProfile.toResponse()
        } catch(ex: Exception){
            throw Exception(ex.message + "3")
        }
    }
}
