package io.avdev.domain.usecase.item

import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.repository.ShoppingItemRepository

class GetItemsByListIdUseCase(private val repository : ShoppingItemRepository) {

    suspend fun execute(id: Int): List<ShoppingItem> {
        return repository.getItemsByListId(id)
    }
}