package com.vauldex.inventory_management.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecuredSecurityConfig {

    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            securityMatcher("/api/products")
            authorizeHttpRequests {
                authorize("/api/products", permitAll)
                authorize("/api/products", authenticated)
            }
            formLogin {
                loginPage = "/api/sessions"
                loginProcessingUrl = "/api/sessions"
                permitAll = true
            }
            logout {
                logoutUrl = "/api/sessions/logout"
                logoutSuccessUrl = "/api/sessions"
            }

        }

        return http.build()
    }
}