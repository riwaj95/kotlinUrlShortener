package com.kotlin.URLshorten.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class UrlEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val originalUrl: String,

    @Column(unique = true, nullable = false)
    val shortId: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
