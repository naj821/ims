package com.vauldex.inventory_management.domain.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class ProfileReponse(
    val id: UUID?,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val createdAt: LocalDateTime?
)

data class ProfileUpdateResponse(
    val profileResponse: ProfileReponse
)
