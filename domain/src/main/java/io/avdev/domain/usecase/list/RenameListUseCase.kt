package io.avdev.domain.usecase.list

import io.avdev.domain.repository.ShoppingListRepository

class RenameListUseCase(private val repository: ShoppingListRepository) {

    suspend fun execute(id: Int, newName: String) {
        repository.renameShoppingList(id, newName)
    }
}