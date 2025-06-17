package com.vauldex.inventory_management.domain.dto.response

import java.util.UUID

data class UserResponse(val email: String, val role: String, val id: UUID)
data class Tokens(val accessToken: String, val refreshToken: String)
data class LoginResponse(val userResponse: UserResponse, val authorization: Tokens)
