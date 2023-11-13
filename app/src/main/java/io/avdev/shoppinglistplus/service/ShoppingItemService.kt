package io.avdev.shoppinglistplus.service

import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.usecase.item.AddItemUseCase
import io.avdev.domain.usecase.item.SelectItemsUseCase

class ShoppingItemService
    (private val addItemUseCase: AddItemUseCase,
    private val selectItemsUseCase: SelectItemsUseCase)
{
    fun addItem(item: ShoppingItem) {
        addItemUseCase.execute(item)
    }

    fun selectItems(id: Int): List<ShoppingItem> {
        return selectItemsUseCase.execute(id)
    }
}