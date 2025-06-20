package com.vauldex.inventory_management.domain.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "profiles")
class ProfileEntity (
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        var id: UUID? = null,
        @OneToOne(cascade = [CascadeType.REMOVE])
        @MapsId
        @JoinColumn(name = "user_id")
        val userEntity: UserEntity = UserEntity(),
        @Column(name ="first_name")
        var firstName: String = "",
        @Column(nullable = true)
        var middleName: String? = null,
        @Column(name = "last_name")
        var lastName: String = "",
        @Column(name = "created_at")
        @CreationTimestamp
        var createdAt: LocalDateTime? = null
)