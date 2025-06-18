package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.ProductEditRequest
import com.vauldex.inventory_management.domain.dto.request.ProductRequest
import com.vauldex.inventory_management.domain.dto.response.ProductResponse
import com.vauldex.inventory_management.domain.entity.ProductEntity
import com.vauldex.inventory_management.response.ResponseSuccess
import com.vauldex.inventory_management.service.abstraction.AuthenticationService
import com.vauldex.inventory_management.service.abstraction.ProductService
import com.vauldex.inventory_management.utility.JwtUtils
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/products")
class ProductController(
        private val prodService: ProductService,
        private val authService: AuthenticationService
) {

    @PostMapping
    fun save(
            @CookieValue("jwt") jwt: String,
            @RequestBody product: ProductRequest
    ): ResponseSuccess<String> {

//        val auth = authHeader.removePrefix("Bearer ").trim()

        authService.validateAccessToken(jwt)

        val category = prodService.getCategoryName(category = product.category)
        val prod = prodService.save(product = product.toEntity(cat = category))

        return ResponseSuccess(
            code = "PRODUCT_SAVED",
            status = HttpStatus.OK,
            data = prod
        )
    }

    @GetMapping("/{productName}")
    fun search(
            @PathVariable productName: String,
            @CookieValue("jwt") jwt: String
    ): ResponseSuccess<ProductResponse>{

//        val auth = authHeader.removePrefix("Bearer ").trim()

        authService.validateAccessToken(jwt)

        val prod = prodService.search(product = productName)

        return ResponseSuccess(
            code = "PRODUCT_FOUND",
            status = HttpStatus.OK,
            data = prod
        )
    }

    @PutMapping
    fun edit(
            @RequestBody product: ProductEditRequest,
            @CookieValue("jwt") jwt: String
    ): ResponseSuccess<String> {

//        val auth = authHeader.removePrefix("Bearer ").trim()

        authService.validateAccessToken(jwt)

        val category = prodService.getCategoryName(category = product.category)
        val prod = prodService.editProduct(product = product.toEntity(cat = category))

        return ResponseSuccess(
            code = "PRODUCT_EDITED",
            status = HttpStatus.OK,
            data = prod
        )
    }

    @DeleteMapping
    fun delete(
            @RequestParam idProduct: UUID,
            @CookieValue("jwt") jwt: String
    ): ResponseSuccess<String> {

//        val auth = authHeader.removePrefix("Bearer ").trim()

        authService.validateAccessToken(jwt)

        val prod = prodService.deleteProduct(idProduct)
        val response = ResponseSuccess(
                code = "PRODUCT_DELETED",
                status = HttpStatus.OK,
                data = prod
        )
        return response
    }

    @GetMapping
    fun get(@CookieValue("jwt") jwt: String): ResponseSuccess<List<ProductEntity>> {
//        val auth = authHeader.removePrefix("Bearer ").trim()

        authService.validateAccessToken(jwt)



        val productResponse = prodService.getAllProduct()

        return ResponseSuccess(
            code = "PRODUCT_RETRIEVED",
            status = HttpStatus.OK,
            data = productResponse
        )
    }
}