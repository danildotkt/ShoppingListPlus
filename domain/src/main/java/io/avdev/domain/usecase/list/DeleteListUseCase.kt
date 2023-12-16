package io.avdev.domain.usecase.list

import io.avdev.domain.model.ShoppingList
import io.avdev.domain.repository.ShoppingListRepository

class DeleteListUseCase(private val repository: ShoppingListRepository) {

    suspend fun execute(list: ShoppingList) {
        repository.deleteShoppingList(list)
    }
}