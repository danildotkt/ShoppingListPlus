package io.avdev.data.mapper

import androidx.room.TypeConverter
import io.avdev.data.entity.ShoppingListEntity
import io.avdev.domain.model.ShoppingList

class ShoppingListMapper {

    @TypeConverter
    fun mapToEntity(model: ShoppingList): ShoppingListEntity {
        return ShoppingListEntity(
            id = model.id,
            name = model.name,
        )
    }

    @TypeConverter
    fun mapToModel(entity: ShoppingListEntity): ShoppingList {
        return ShoppingList(
            id = entity.id,
            name = entity.name,
        )
    }

    @TypeConverter
    fun mapToModels(entities: List<ShoppingListEntity>): List<ShoppingList> {
        return entities.map { mapToModel(it) }
    }


}
