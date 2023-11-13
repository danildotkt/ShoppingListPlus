package io.avdev.domain.repository

import io.avdev.domain.model.ShoppingItem

interface ShoppingItemRepository {
    fun createShoppingItem(item: ShoppingItem)
    fun selectShoppingItems(id: Int) : List<ShoppingItem>

}