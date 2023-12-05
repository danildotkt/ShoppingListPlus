package io.avdev.data.repository

import io.avdev.data.dao.ShoppingItemDao
import io.avdev.data.mapper.ShoppingItemMapper
import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.repository.ShoppingItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShoppingItemRepositoryImpl(
    private val dao: ShoppingItemDao,
    private val mapper: ShoppingItemMapper) : ShoppingItemRepository{

    override suspend fun createItem(item: ShoppingItem) {
        val entity = mapper.mapToEntity(item)
        dao.insertItem(entity)
    }

    override fun getItemsByListId(id: Int): Flow<List<ShoppingItem>> {
        return dao.getItemsByListId(id).map { mapper.mapToModels(it) }
    }

    override suspend fun updateItemSelection(itemId: Int, isSelected: Boolean) {
        dao.updateItemSelection(itemId, isSelected)
    }

    override suspend fun clearSelectionByListId(id: Int) {
        dao.clearSelectionByListId(id)
    }

    override fun getSelectedItemCount(id: Int): Flow<Int> {
        return dao.getSelectedItemCount(id)
    }

    override fun getTotalItemCount(id: Int): Flow<Int> {
        return dao.getTotalItemCount(id)
    }
}