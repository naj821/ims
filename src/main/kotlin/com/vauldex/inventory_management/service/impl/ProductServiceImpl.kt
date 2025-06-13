package com.vauldex.inventory_management.service.impl

import com.vauldex.inventory_management.domain.dto.response.ProductResponse
import com.vauldex.inventory_management.domain.entity.CategoryEntity
import com.vauldex.inventory_management.domain.entity.ProductEntity
import com.vauldex.inventory_management.repository.CategoryRepository
import com.vauldex.inventory_management.repository.ProductRepository
import com.vauldex.inventory_management.service.abstraction.ProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(private val productRepo: ProductRepository,
                         private  val categoryRepo: CategoryRepository): ProductService {

    override fun getCategoryName(category: String): CategoryEntity {
        try {
            val doesExists = categoryRepo.existsByName(category)

            if(!doesExists) throw IllegalArgumentException("Category does not exists.")

            val cat = categoryRepo.findByName(category)

            val category = cat.id?.let { categoryRepo.getReferenceById(it) }

            return category.let { cat }
        } catch (error: IllegalArgumentException) {
            throw IllegalArgumentException(error.message)
        }
    }

    override fun save(product: ProductEntity): String {
        try{
            val doesExists = productRepo.existsByProductName(product.productName)

            if (doesExists) throw IllegalArgumentException("Product already exists.")

            productRepo.saveAndFlush(product)
            return "Product saved."
        } catch (error: IllegalArgumentException) {
            throw IllegalArgumentException(error.message)
        }
    }

    override fun search(product: String): ProductResponse {
        try {
            val doesExists = productRepo.existsByProductName(product)

            if (!doesExists) throw IllegalArgumentException("No product found.")

            val prod = productRepo.findByProductName(product)
            return prod.toResponse()
        } catch (error: IllegalArgumentException) {
            throw IllegalArgumentException("This error: ${error.message}")
        }
    }

    override fun edit(product: ProductEntity): String {
        TODO("Not yet implemented")
    }
}