package com.vauldex.inventory_management.domain.dto.request

import com.vauldex.inventory_management.domain.entity.CategoryEntity
import com.vauldex.inventory_management.domain.entity.ProductEntity
import java.util.UUID

data class ProductEditRequest(val id: UUID, val productName: String, val quantity: Long, val category: String)

{
    fun toEntity( cat: CategoryEntity): ProductEntity = ProductEntity(
            id = id,
            productName = productName,
            quantity = quantity,
            category = cat
    )
}