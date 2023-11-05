package io.avdev.domain.repository

import io.avdev.domain.model.ShoppingList

interface ShoppingListRepository {
    fun createShoppingList(list: ShoppingList)
    fun selectShoppingLists() : List<ShoppingList>
    fun deleteShoppingList(list : ShoppingList)
    fun renameShoppingList(list : ShoppingList)
}