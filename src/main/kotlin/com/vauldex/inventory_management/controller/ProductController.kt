package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.ProductRequest
import com.vauldex.inventory_management.domain.dto.response.ProductResponse
import com.vauldex.inventory_management.service.abstraction.ProductService
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(private val prodService: ProductService) {

    @PostMapping
    fun save(@RequestBody product: ProductRequest): ResponseEntity<String> {
        val category = prodService.getCategoryName(category = product.category)
        val prod = prodService.save(product = product.toEntity(cat = category))
        return ResponseEntity.status(HttpStatus.CREATED).body(prod)
    }

    @GetMapping
    fun search(@RequestParam productName: String): ResponseEntity<ProductResponse> {
        val prod = prodService.search(product = productName)
        return ResponseEntity.status(HttpStatus.OK).body(prod)
    }
}