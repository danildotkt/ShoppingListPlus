package io.avdev.domain.usecase.item

import io.avdev.domain.repository.ShoppingProductRepository

class UpdateProductSelectionUseCase(private val repository: ShoppingProductRepository) {

    suspend fun execute(id: Int, isSelected: Boolean) {
        repository.updateProductSelection(id, isSelected)
    }
}