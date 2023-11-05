package io.avdev.domain.usecase.list

import io.avdev.domain.model.ShoppingList
import io.avdev.domain.repository.ShoppingItemRepository
import io.avdev.domain.repository.ShoppingListRepository

class AddListUseCase (private val repository : ShoppingListRepository){

    fun execute(list : ShoppingList){
        repository.createShoppingList(list)
    }

}