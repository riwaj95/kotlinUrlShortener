package com.kotlin.URLshorten.repository

import com.kotlin.URLshorten.model.UrlEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository : JpaRepository<UrlEntity, Long> {

    fun findByshortId(shortId : String): UrlEntity?
}