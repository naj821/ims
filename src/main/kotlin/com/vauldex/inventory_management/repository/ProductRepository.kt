package com.vauldex.inventory_management.repository

import com.vauldex.inventory_management.domain.entity.CategoryEntity
import com.vauldex.inventory_management.domain.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface ProductRepository: JpaRepository<ProductEntity, UUID> {
    fun existsByProductName(item: String): Boolean
    fun findByProductName(item: String): ProductEntity
}