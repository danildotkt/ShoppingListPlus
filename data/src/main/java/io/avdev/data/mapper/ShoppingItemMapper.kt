package io.avdev.data.mapper

import androidx.room.TypeConverter
import io.avdev.data.entity.ShoppingItemEntity
import io.avdev.domain.model.ShoppingItem

class ShoppingItemMapper {
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
    fun mapToModels(entities: List<ShoppingItemEntity>): List<ShoppingItem> {
        return entities.map { mapToModel(it) }
    }
}