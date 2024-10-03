package com.kotlin.URLshorten.controller

import com.kotlin.URLshorten.model.UrlEntity
import com.kotlin.URLshorten.service.UrlService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/url")
class UrlController(@Autowired private val urlService: UrlService) {


    @PostMapping("/shorten")
    fun shortenUrl(@RequestBody request: Map<String, String>): ResponseEntity<String> {
        val originalUrl = request["originalUrl"]
        return if (originalUrl.isNullOrEmpty()) {
            ResponseEntity.badRequest().body("Invalid URL")
        } else {
            val shortId = urlService.shortenUrl(originalUrl)
            val shortUrl = "http://short.url/$shortId"
            ResponseEntity.ok(shortUrl)
        }
    }

    @GetMapping("/{shortId}")
    fun redirectUrl(@PathVariable shortId: String, response: HttpServletResponse) {
        val originalUrl = urlService.getOriginalUrl(shortId)
        response.sendRedirect(originalUrl)
    }

    @GetMapping("/all")
    fun getAllUrls(): ResponseEntity<List<UrlEntity>> {
        val allUrls = urlService.getAllUrls()
        return ResponseEntity.ok(allUrls)
    }

}