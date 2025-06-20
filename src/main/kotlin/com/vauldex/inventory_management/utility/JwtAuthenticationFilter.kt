package com.vauldex.inventory_management.utility

import com.vauldex.inventory_management.service.abstraction.AuthenticationService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val authService: AuthenticationService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = extractJwtFromCookie(request)

            if (jwt != null && SecurityContextHolder.getContext().authentication == null) {
                authService.validateAccessToken(jwt)

                val authToken = UsernamePasswordAuthenticationToken(
                    jwt,
                    null,
                    emptyList()
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }

        filterChain.doFilter(request, response)
    }

    private fun extractJwtFromCookie(request: HttpServletRequest): String? {
        return request.cookies?.find { it.name == "jwt" }?.value
    }
}