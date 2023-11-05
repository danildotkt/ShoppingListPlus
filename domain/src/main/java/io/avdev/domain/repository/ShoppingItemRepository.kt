package io.avdev.domain.repository

import io.avdev.domain.model.ShoppingItem

interface ShoppingItemRepository {
    fun createShoppingItem(item: ShoppingItem)
    fun selectShoppingItems() : List<ShoppingItem>
    fun deleteShoppingItem(item : ShoppingItem)
    fun renameShoppingItem(item : ShoppingItem)
    fun isSelectShoppingItem(item : ShoppingItem)
    fun isUnselectShoppingItem(item : ShoppingItem)
}