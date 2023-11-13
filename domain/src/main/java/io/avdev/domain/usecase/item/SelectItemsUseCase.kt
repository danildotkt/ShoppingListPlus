package io.avdev.domain.usecase.item

import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.repository.ShoppingItemRepository

class SelectItemsUseCase (private val repository : ShoppingItemRepository){

    fun execute(id: Int) : List<ShoppingItem>{
        return repository.selectShoppingItems(id)
    }
}