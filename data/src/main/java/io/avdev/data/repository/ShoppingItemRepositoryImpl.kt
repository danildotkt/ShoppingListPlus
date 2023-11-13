package io.avdev.data.repository

import io.avdev.data.dao.ShoppingItemDao
import io.avdev.data.mapper.ShoppingItemMapper
import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.repository.ShoppingItemRepository

class ShoppingItemRepositoryImpl(private val dao: ShoppingItemDao,
                                 private val mapper: ShoppingItemMapper) : ShoppingItemRepository{

    override fun createShoppingItem(item: ShoppingItem) {
        val entity = mapper.mapToEntity(item)
        dao.insertItem(entity)
    }

    override fun selectShoppingItems(id : Int): List<ShoppingItem> {
        val entityList = dao.selectItems(id)
        return mapper.mapToModels(entityList)
    }
}