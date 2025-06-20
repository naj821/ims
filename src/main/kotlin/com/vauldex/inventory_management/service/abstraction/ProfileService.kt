package com.vauldex.inventory_management.service.abstraction

import com.vauldex.inventory_management.domain.entity.ProfileEntity

interface ProfileService {
    fun save(profileEntity: ProfileEntity): Unit
}