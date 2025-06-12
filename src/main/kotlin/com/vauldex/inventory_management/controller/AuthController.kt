package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.UserRequest
import com.vauldex.inventory_management.domain.dto.response.UserResponse
import com.vauldex.inventory_management.service.abstraction.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.slf4j.LoggerFactory

@RestController
@RequestMapping("/api/sessions")
class AuthController(private val userService: UserService) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/dd")
    fun create(@RequestBody user: UserRequest): ResponseEntity<UserResponse> {
      logger.info("*".repeat(100))
      logger.info("aas")
      logger.info("*".repeat(100))
      val user1 = userService.create(user.toEntity())

      return ResponseEntity.status(HttpStatus.CREATED).body(user1)
    }

}
