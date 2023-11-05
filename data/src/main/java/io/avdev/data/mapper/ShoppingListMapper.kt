package io.avdev.data.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.avdev.data.entity.ShoppingItemEntity
import io.avdev.data.entity.ShoppingListEntity
import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.model.ShoppingList

class ShoppingListMapper {
    @TypeConverter
    fun fromShoppingItemEntityList(shoppingItemEntityList: List<ShoppingItemEntity>?): String? {
        if (shoppingItemEntityList == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<ShoppingItemEntity>>() {}.type
        return gson.toJson(shoppingItemEntityList, type)
    }

    @TypeConverter
    fun toShoppingItemEntityList(json: String?): List<ShoppingItemEntity>? {
        if (json == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<ShoppingItemEntity>>() {}.type
        return gson.fromJson(json, type)
    }
    @TypeConverter
    fun mapToEntity(model: ShoppingList): ShoppingListEntity {
        return ShoppingListEntity(
            id = model.id,
            name = model.name,
            itemList = model.itemList?.let { mapShoppingItemModelsToEntities(it) }
        )
    }
    @TypeConverter
    fun mapToModel(entity: ShoppingListEntity): ShoppingList {
        return ShoppingList(
            id = entity.id,
            name = entity.name,
            itemList = entity.itemList?.let { mapShoppingItemEntitiesToModels(it) }
        )
    }
    @TypeConverter
    fun mapToEntities(models: List<ShoppingList>): List<ShoppingListEntity> {
        return models.map { mapToEntity(it) }
    }
    @TypeConverter
    fun mapToModels(entities: List<ShoppingListEntity>): List<ShoppingList> {
        return entities.map { mapToModel(it) }
    }
    private fun mapShoppingItemEntitiesToModels(entities: List<ShoppingItemEntity>): List<ShoppingItem> {
        return entities.map { ShoppingItem(it.id, it.listId, it.name) }
    }
    private fun mapShoppingItemModelsToEntities(models: List<ShoppingItem>): List<ShoppingItemEntity> {
        return models.map { ShoppingItemEntity(it.id, it.listId, it.name) }
    }
}