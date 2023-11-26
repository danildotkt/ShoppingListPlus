package io.avdev.data.repository

import io.avdev.data.dao.ShoppingListDao
import io.avdev.data.mapper.ShoppingListMapper
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.repository.ShoppingListRepository

class ShoppingListRepositoryImpl
    (private val dao: ShoppingListDao, private val mapper : ShoppingListMapper)
    : ShoppingListRepository{

    override fun createShoppingList(list: ShoppingList) {
        val entity = mapper.mapToEntity(list)
        dao.insertShoppingList(entity)
    }

    override fun selectShoppingLists(): List<ShoppingList> {
        val entityList = dao.getShoppingLists()
        return mapper.mapToModels(entityList)
    }

    override fun deleteShoppingList(list: ShoppingList) {
        val entity = mapper.mapToEntity(list)
        dao.deleteShoppingList(entity)
    }

    override fun updateShoppingList(list: ShoppingList) {
        val entity = mapper.mapToEntity(list)
        dao.updateShoppingList(entity)
    }
}