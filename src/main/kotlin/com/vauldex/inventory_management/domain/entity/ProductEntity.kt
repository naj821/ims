package com.vauldex.inventory_management.domain.entity

import com.vauldex.inventory_management.domain.dto.response.ProductResponse
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "products")
class ProductEntity (
        @Column(name = "product_name")
        var productName: String = "",
        @Column(name = "quantity")
        var quantity: Long = 0,
        @Column(name = "id")
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        var id: UUID? = null,
        @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
        @JoinColumn(name = "category_id")
        var category: CategoryEntity? = null,
        @Column(name = "created_at")
        @CreationTimestamp
        var createdAt: LocalDateTime? = null
)

{
        fun toResponse(): ProductResponse = ProductResponse(productName = productName, quantity = quantity, id = id!!)

}
