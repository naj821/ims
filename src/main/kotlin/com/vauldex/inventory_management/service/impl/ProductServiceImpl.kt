package com.vauldex.inventory_management.service.impl

import com.vauldex.inventory_management.domain.dto.request.ProductEditRequest
import com.vauldex.inventory_management.domain.dto.response.ProductResponse
import com.vauldex.inventory_management.domain.entity.CategoryEntity
import com.vauldex.inventory_management.domain.entity.ProductEntity
import com.vauldex.inventory_management.repository.CategoryRepository
import com.vauldex.inventory_management.repository.ProductRepository
import com.vauldex.inventory_management.service.abstraction.ProductService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProductServiceImpl(private val productRepo: ProductRepository,
                         private  val categoryRepo: CategoryRepository): ProductService {

    override fun getCategoryName(category: String): CategoryEntity {
        try {
            val doesExists = categoryRepo.existsByName(category)

            if(!doesExists) throw IllegalArgumentException("Category does not exists.")

            val cat = categoryRepo.findByName(category)

            val category1 = cat.id?.let { categoryRepo.getReferenceById(it) }

            return category1.let { cat }
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

    override fun editProduct(product: ProductEntity): String {
        try{
            val doesExists = productRepo.findById(product.id!!).orElse(null)

            if (doesExists == null) throw IllegalArgumentException("Product does not exists.")

            productRepo.saveAndFlush(ProductEntity(
                    productName = product.productName,
                    quantity = product.quantity,
                    id = product.id,
                    category = doesExists.category,
                    createdAt = doesExists.createdAt
                    ))
            return "Product successfully edited."
        } catch (error: IllegalArgumentException) {
            throw IllegalArgumentException(error.message)
        }
    }

    override fun deleteProduct(id: UUID): String {
        try {
            val doesExists = productRepo.existsById(id)

            if(!doesExists) throw IllegalArgumentException("Product does not exists.")

            productRepo.deleteById(id)
            return "Product successfully deleted."
        } catch (error: IllegalArgumentException) {
            throw IllegalArgumentException(error.message)
        }
    }

    override fun getAllProduct(): List<ProductEntity> {
        try {
            val getProducts = productRepo.findAll()

            return getProducts
        } catch (error: IllegalArgumentException) {
            throw IllegalArgumentException(error.message)
        }
    }
}
