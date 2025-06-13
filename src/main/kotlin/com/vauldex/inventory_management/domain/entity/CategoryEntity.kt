package com.vauldex.inventory_management.domain.entity

import com.vauldex.inventory_management.domain.dto.response.ProductResponse
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "categories")
class CategoryEntity (
        @Column(name = "name")
        var name: String = "",
        @Column(name = "id")
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        var id: UUID? = null,
        @OneToMany(mappedBy = "id", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var products: List<ProductEntity> = emptyList(),
        @Column(name = "created_at")
        @CreationTimestamp
        var createdAt: LocalDateTime? = null
)