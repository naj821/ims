package com.vauldex.inventory_management.domain.dto.response

import com.vauldex.inventory_management.domain.dto.request.TokenRequest
import java.util.UUID

data class UserResponse(val email: String, val role: String, val id: UUID)
data class LoginResponse(val userResponse: UserResponse, val authorization: TokenRequest)
