package com.vauldex.inventory_management.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "refresh_token")
class RefreshTokenEntity (
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
    @Column(name ="hashed_token")
    val hashToken: String = "",
    @Column(name = "expires_at")
    val expiresAt: Instant,
    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime? = null
)