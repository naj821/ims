package com.vauldex.inventory_management.utility

import jakarta.servlet.http.Cookie
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CookieUtils(
    @Value("\${cookie.max-age-seconds}") private val cookieMaxAge: Int
) {
    
    fun createJwtCookie(token: String): Cookie {
        val cookie = Cookie("jwt", token)
        cookie.isHttpOnly = true
        cookie.secure = true
        cookie.path = "/"
        cookie.maxAge = cookieMaxAge
        return cookie
    }
    
    fun createExpiredJwtCookie(): Cookie {
        val cookie = Cookie("jwt", "")
        cookie.isHttpOnly = true
        cookie.secure = true
        cookie.path = "/"
        cookie.maxAge = 0
        return cookie
    }
}
