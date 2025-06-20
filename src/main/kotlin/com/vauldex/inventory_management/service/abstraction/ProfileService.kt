package com.vauldex.inventory_management.service.abstraction

import com.vauldex.inventory_management.domain.dto.request.ProfileCreateRequest
import com.vauldex.inventory_management.domain.dto.request.ProfileUpdateRequest
import com.vauldex.inventory_management.domain.dto.response.ProfileReponse
import com.vauldex.inventory_management.domain.dto.response.ProfileUpdateResponse
import java.util.UUID


interface ProfileService {
    fun create(userId: UUID, profile: ProfileCreateRequest): ProfileReponse
    fun update(userId: UUID, profile: ProfileUpdateRequest): ProfileUpdateResponse
    fun find(userId: UUID): ProfileReponse
}