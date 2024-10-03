package com.kotlin.URLshorten.service

import com.kotlin.URLshorten.model.UrlEntity
import com.kotlin.URLshorten.repository.UrlRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UrlService(private val urlRepository: UrlRepository) {

    fun shortenUrl(originalUrl: String): String {
        val shortId = encodeBase62(System.currentTimeMillis())
        val urlEntity = UrlEntity(
            originalUrl = originalUrl,
            shortId = shortId,
            createdAt = LocalDateTime.now()
        )
        urlRepository.save(urlEntity)
        return shortId
    }

    fun getAllUrls(): List<UrlEntity> = urlRepository.findAll()

    @Cacheable(value = ["urls"], key = "#shortId")
    fun getOriginalUrl(shortId: String): String =
        urlRepository.findByshortId(shortId)?.originalUrl
            ?: throw RuntimeException("Short URL not found")

    private fun encodeBase62(id: Long): String {
        val characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var num = id
        val shortUrl = StringBuilder()
        while (num > 0) {
            shortUrl.append(characters[(num % 62).toInt()])
            num /= 62
        }
        return shortUrl.reverse().toString()
    }

}