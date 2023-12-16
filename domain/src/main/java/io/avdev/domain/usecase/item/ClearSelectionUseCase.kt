package io.avdev.domain.usecase.item

import io.avdev.domain.repository.ShoppingProductRepository

class ClearSelectionUseCase(private val repository: ShoppingProductRepository) {

    suspend fun execute(id: Int) {
        repository.clearSelectionByListId(id)
    }
}