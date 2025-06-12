package com.vauldex.inventory_management.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "categories")
class Category (
        @Column(name = "name")
        var name: String,
        @Column(name = "id")
        var id: UUID = UUID.randomUUID(),
        @Column(name = "created_at")
        var createdAt: LocalDateTime = LocalDateTime.now()
)