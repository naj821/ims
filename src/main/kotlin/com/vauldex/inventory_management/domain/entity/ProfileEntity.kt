package com.vauldex.inventory_management.domain.entity

import com.vauldex.inventory_management.domain.dto.response.ProfileReponse
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "profiles")
class ProfileEntity (
        @Column(name ="first_name")
        var firstName: String = "",
        @Column(nullable = false)
        var middleName: String = "",
        @Column(name = "last_name")
        var lastName: String = "",
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,
        @OneToOne(cascade = [CascadeType.REMOVE])
        @JoinColumn(name = "user_id")
        val userEntity: UserEntity? = null,
        @Column(name = "created_at")
        @CreationTimestamp
        var createdAt: LocalDateTime? = null
) {
        fun toResponse(): ProfileReponse = ProfileReponse(
                id = userEntity?.id,
                firstName = firstName,
                middleName = middleName,
                lastName = lastName,
                createdAt = createdAt
        )
}