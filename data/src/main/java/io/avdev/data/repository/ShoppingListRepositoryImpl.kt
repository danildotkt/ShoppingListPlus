package io.avdev.data.repository

import io.avdev.data.dao.ShoppingListDao
import io.avdev.data.mapper.ShoppingListMapper
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShoppingListRepositoryImpl(
    private val dao: ShoppingListDao,
    private val mapper: ShoppingListMapper
) : ShoppingListRepository {

    override suspend fun createShoppingList(list: ShoppingList) {
        val entity = mapper.mapToEntity(list)
        dao.insertShoppingList(entity)
    }

    override fun getShoppingLists(): Flow<List<ShoppingList>> {
        return dao.getShoppingLists().map { mapper.mapToModels(it) }
    }

    override suspend fun deleteShoppingList(list: ShoppingList) {
        val entity = mapper.mapToEntity(list)
        dao.deleteShoppingList(entity)
    }

    override suspend fun renameShoppingList(id: Int, newName: String) {
        dao.renameShoppingList(id, newName)
    }
}