package com.vauldex.inventory_management.domain.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "products")
class Product (
        @Column(name = "product_name")
        var productName: String,
        @Column(name = "quantity")
        var quantity: Int = 0,
        @Column(name = "id")
        var id: UUID = UUID.randomUUID(),
        @ManyToOne(cascade = [CascadeType.REMOVE])
        @JoinColumn(name = "category_id")
        var categoryId: Category,
        @Column(name = "created_at")
        var createdAt: LocalDateTime = LocalDateTime.now()
)