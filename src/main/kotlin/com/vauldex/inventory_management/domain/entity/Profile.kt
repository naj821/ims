package com.vauldex.inventory_management.domain.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "profiles")
class Profile (
        @Column(name ="first_name")
        var firstName: String,
        @Column(nullable = false)
        var middleName: String,
        @Column(name = "last_name")
        var lastName: String,
        @OneToOne(cascade = [CascadeType.REMOVE])
        @JoinColumn(name = "user_id")
        val user: User,
        @Column(name = "created_at")
        var createdAt: LocalDateTime = LocalDateTime.now()
)