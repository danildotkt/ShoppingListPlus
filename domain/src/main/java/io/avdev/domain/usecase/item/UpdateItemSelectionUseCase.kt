package io.avdev.domain.usecase.item

import io.avdev.domain.repository.ShoppingItemRepository

class UpdateItemSelectionUseCase(private val repository : ShoppingItemRepository) {

    suspend fun execute(id : Int, isSelected : Boolean) {
        repository.updateItemSelection(id, isSelected)
    }
}