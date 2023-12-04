package io.avdev.domain.repository

import io.avdev.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
    suspend fun createShoppingList(list : ShoppingList)
    fun getShoppingLists() : Flow<List<ShoppingList>>
    suspend fun deleteShoppingList(list : ShoppingList)
    suspend fun renameShoppingList(id : Int, newName : String)
}