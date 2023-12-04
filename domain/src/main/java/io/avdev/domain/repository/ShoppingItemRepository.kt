package io.avdev.domain.repository

import io.avdev.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingItemRepository {
    suspend fun createItem(item: ShoppingItem)
    suspend fun updateItemSelection(itemId : Int, isSelected : Boolean)
    suspend fun clearSelectionByListId(id: Int)
    suspend fun getItemsByListId(id: Int) : List<ShoppingItem>
    fun getSelectedItemCount(id: Int): Flow<Int>
    fun getTotalItemCount(id: Int): Flow<Int>
}