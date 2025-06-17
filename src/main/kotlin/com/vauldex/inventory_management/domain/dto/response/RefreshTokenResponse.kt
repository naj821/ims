package com.vauldex.inventory_management.domain.dto.response

import java.util.UUID

data class RefreshTokenResponse(val id: UUID, val hashedAccessToken: String, val hashedRefreshToken: String)
