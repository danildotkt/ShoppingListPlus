package io.avdev.data.mapper

import androidx.room.TypeConverter
import io.avdev.data.entity.ProductEntity
import io.avdev.domain.model.Product

class ProductMapper {
    @TypeConverter
    fun mapToEntity(model: Product): ProductEntity {
        return ProductEntity(
            id = model.id,
            listId = model.listId,
            name = model.name,
            isSelected = model.isSelected
        )
    }

    @TypeConverter
    fun mapToModel(entity: ProductEntity): Product {
        return Product(
            id = entity.id,
            listId = entity.listId,
            name = entity.name,
            isSelected = entity.isSelected
        )
    }

    @TypeConverter
    fun mapToModels(entities: List<ProductEntity>): List<Product> {
        return entities.map { mapToModel(it) }
    }
}