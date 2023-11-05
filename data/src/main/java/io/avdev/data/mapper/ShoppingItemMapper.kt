package io.avdev.data.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import io.avdev.data.entity.ShoppingItemEntity
import io.avdev.data.entity.ShoppingListEntity
import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.model.ShoppingList

class ShoppingItemMapper {
    @TypeConverter
    fun fromEntity(entity: ShoppingItemEntity): String {
        val gson = Gson()
        return gson.toJson(entity)
    }

    @TypeConverter
    fun toEntity(json: String): ShoppingItemEntity {
        val gson = Gson()
        return gson.fromJson(json, ShoppingItemEntity::class.java)
    }
    @TypeConverter
    fun mapToEntity(model: ShoppingItem): ShoppingItemEntity {
        return ShoppingItemEntity(
            id = model.id,
            listId = model.listId,
            name = model.name,
            isSelected = model.isSelected
        )
    }
    @TypeConverter
    fun mapToModel(entity: ShoppingItemEntity): ShoppingItem {
        return ShoppingItem(
            id = entity.id,
            listId = entity.listId,
            name = entity.name,
            isSelected = entity.isSelected
        )
    }
    @TypeConverter
    fun mapToEntities(models: List<ShoppingItem>): List<ShoppingItemEntity> {
        return models.map { mapToEntity(it) }
    }
    @TypeConverter
    fun mapToModels(entities: List<ShoppingItemEntity>): List<ShoppingItem> {
        return entities.map { mapToModel(it) }
    }
}