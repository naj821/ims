package com.vauldex.inventory_management.repository

import com.vauldex.inventory_management.domain.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CategoryRepository: JpaRepository<CategoryEntity, UUID> {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): CategoryEntity
}