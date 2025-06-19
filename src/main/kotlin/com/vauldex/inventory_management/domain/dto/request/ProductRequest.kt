package com.vauldex.inventory_management.domain.dto.request

import com.vauldex.inventory_management.domain.entity.CategoryEntity
import com.vauldex.inventory_management.domain.entity.ProductEntity

data class ProductRequest(val productName: String, val quantity: Long, val category: String)

{
    fun toEntity( cat: CategoryEntity): ProductEntity = ProductEntity(
            productName = productName,
            quantity = quantity,
            category = cat
            )
}
