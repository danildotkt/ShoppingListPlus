package io.avdev.domain.usecase.item

import io.avdev.domain.model.Product
import io.avdev.domain.repository.ShoppingProductRepository

class GetProductsByListIdUseCase(private val repository: ShoppingProductRepository) {

    suspend fun execute(id: Int): List<Product> {
        return repository.getProductsByListId(id)
    }
}