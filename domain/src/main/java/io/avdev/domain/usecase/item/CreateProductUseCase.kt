package io.avdev.domain.usecase.item

import io.avdev.domain.model.Product
import io.avdev.domain.repository.ShoppingProductRepository

class CreateProductUseCase(private val repository: ShoppingProductRepository) {

    suspend fun execute(item: Product) {
        repository.createProduct(item)
    }
}