package io.avdev.domain.usecase.list

import io.avdev.domain.model.ShoppingList
import io.avdev.domain.repository.ShoppingListRepository

class UpdateListUseCase(private val repository : ShoppingListRepository) {

    fun execute(list: ShoppingList) {
        repository.renameShoppingList(list)
    }
}