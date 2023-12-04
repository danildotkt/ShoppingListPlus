package io.avdev.domain.usecase.item

import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.repository.ShoppingItemRepository

class CreateItemUseCase(private val repository : ShoppingItemRepository) {

    suspend fun execute (item : ShoppingItem) {
        repository.createItem(item)
    }
}