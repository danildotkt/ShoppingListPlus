package io.avdev.domain.usecase.list

import io.avdev.domain.model.ShoppingList
import io.avdev.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow

class GetListsUseCase(private val repository : ShoppingListRepository) {

    fun execute(): Flow<List<ShoppingList>> {
        return repository.getShoppingLists()
    }

}