package io.avdev.domain.usecase.item

import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.repository.ShoppingItemRepository

class AddItemUseCase (private val repository : ShoppingItemRepository) {
    fun execute (item : ShoppingItem) {
        repository.createShoppingItem(item)
    }
}