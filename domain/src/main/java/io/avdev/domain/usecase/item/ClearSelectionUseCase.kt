package io.avdev.domain.usecase.item

import io.avdev.domain.repository.ShoppingItemRepository

class ClearSelectionUseCase(private val repository : ShoppingItemRepository) {

    suspend fun execute(id: Int) {
        repository.clearSelectionByListId(id)
    }
}