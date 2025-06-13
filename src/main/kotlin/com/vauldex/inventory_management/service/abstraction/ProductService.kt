package com.vauldex.inventory_management.service.abstraction

import com.vauldex.inventory_management.domain.dto.response.ProductResponse
import com.vauldex.inventory_management.domain.entity.CategoryEntity
import com.vauldex.inventory_management.domain.entity.ProductEntity

interface ProductService {
    fun save(product: ProductEntity): String
    fun search(product: String): ProductResponse
    fun getCategoryName(category: String): CategoryEntity
    fun edit(product: ProductEntity): String
}