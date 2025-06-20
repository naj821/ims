package com.vauldex.inventory_management.config

import com.vauldex.inventory_management.utility.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
class SecuredSecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {
    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("http://localhost:3000")
        config.addAllowedHeader("*")
        config.addAllowedMethod("OPTIONS")
        config.addAllowedMethod("GET")
        config.addAllowedMethod("POST")
        config.addAllowedMethod("PUT")
        config.addAllowedMethod("DELETE")
        config.addExposedHeader("Access-Control-Allow-Origin")
        config.addExposedHeader("Access-Control-Allow-Credentials")
        config.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)

        return CorsFilter(source)
    }
    
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .cors {  }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { 
                it
                    .requestMatchers(HttpMethod.GET, "/").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/sessions").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/sessions/refresh").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
}