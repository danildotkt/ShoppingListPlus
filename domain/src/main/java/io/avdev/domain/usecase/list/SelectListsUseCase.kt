package io.avdev.domain.usecase.list

import io.avdev.domain.model.ShoppingList
import io.avdev.domain.repository.ShoppingListRepository


class SelectListsUseCase(private val repository : ShoppingListRepository) {

    fun execute() : List<ShoppingList>{
        return repository.selectShoppingLists()
    }
}