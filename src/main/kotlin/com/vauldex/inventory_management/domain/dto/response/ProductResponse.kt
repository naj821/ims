package com.vauldex.inventory_management.domain.dto.response

import java.util.UUID

data class ProductResponse(val productName: String, val quantity: Int, val id: UUID)