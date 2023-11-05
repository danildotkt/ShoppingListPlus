package io.avdev.data.repository

import io.avdev.data.dao.ShoppingListDao
import io.avdev.data.entity.ShoppingListEntity
import io.avdev.data.mapper.ShoppingListMapper
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.repository.ShoppingListRepository

class ShoppingListRepositoryImpl
    (private val shoppingListDao: ShoppingListDao,
     private val mapper : ShoppingListMapper)
    : ShoppingListRepository{

    override fun createShoppingList(list: ShoppingList) {
        val entity = mapper.mapToEntity(list)
        shoppingListDao.insertShoppingList(entity)
    }

    override fun selectShoppingLists(): List<ShoppingList> {
        val entityList = shoppingListDao.getShoppingLists()
        return mapper.mapToModels(entityList)
    }

    override fun deleteShoppingList(list: ShoppingList) {
        val entity = mapper.mapToEntity(list)
        shoppingListDao.deleteShoppingList(entity)
    }

    override fun renameShoppingList(list: ShoppingList) {
        val entity = mapper.mapToEntity(list)
        shoppingListDao.updateShoppingList(entity)
    }
}