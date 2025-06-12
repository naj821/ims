package com.vauldex.inventory_management.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "categories")
class CategoryEntity (
        @Column(name = "name")
        var name: String,
        @Column(name = "id")
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        var id: UUID? = null,
        @Column(name = "created_at")
        @CreationTimestamp
        var createdAt: LocalDateTime? = null
)