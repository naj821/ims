package com.vauldex.inventory_management.domain.entity

import com.vauldex.inventory_management.domain.dto.response.UserResponse
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
@Table(name = "users")
class UserEntity (
        @Column(name = "email")
        var email: String,
        @Column(name = "password")
        var password: String,
        @Column(name ="role")
        var role: String = "staff",
        @Column(name = "id")
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        var id: UUID? = null,
        @Column(name = "created_at")
        @CreationTimestamp
        var createdAt: LocalDateTime? = null
)

{
        fun toResponse(): UserResponse = UserResponse(email = email, role =  role)
}