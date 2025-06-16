package com.vauldex.inventory_management.controller

import com.vauldex.inventory_management.domain.dto.request.ProductEditRequest
import com.vauldex.inventory_management.domain.dto.request.ProductRequest
import com.vauldex.inventory_management.domain.dto.response.ProductResponse
import com.vauldex.inventory_management.response.ResponseSuccess
import com.vauldex.inventory_management.service.abstraction.ProductService
import org.springframework.http.HttpStatus
<<<<<<< HEAD
=======
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
>>>>>>> 75f9ddc (Added delete functionality)
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/products")
class ProductController(private val prodService: ProductService) {

    @PostMapping
    fun save(@RequestBody product: ProductRequest): ResponseSuccess<String> {
        val category = prodService.getCategoryName(category = product.category)
        val prod = prodService.save(product = product.toEntity(cat = category))

        return ResponseSuccess(
            code = "PRODUCT_SAVED",
            status = HttpStatus.OK,
            data = prod
        )
    }

    @GetMapping
    fun search(@RequestParam productName: String): ResponseSuccess<ProductResponse>{
        val prod = prodService.search(product = productName)

        return ResponseSuccess(
            code = "PRODUCT_FOUND",
            status = HttpStatus.OK,
            data = prod
        )
    }

    @PutMapping
    fun edit(@RequestBody product: ProductEditRequest): ResponseSuccess<String> {
        val category = prodService.getCategoryName(category = product.category)
        val prod = prodService.editProduct(product = product.toEntity(cat = category))

        return ResponseSuccess(
            code = "PRODUCT_EDITED",
            status = HttpStatus.OK,
            data = prod
        )
    }

    @DeleteMapping
    fun delete(@RequestParam idProduct: UUID): ResponseSuccess<String> {
        val prod = prodService.deleteProduct(idProduct)
        val response = ResponseSuccess(
                code = "PRODUCT_DELETED",
                status = HttpStatus.OK,
                data = prod
        )
        return response
    }
}