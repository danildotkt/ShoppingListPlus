package io.avdev.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.avdev.data.mapper.ProductMapper

@Entity(
    tableName = "items",
    foreignKeys = [ForeignKey(
        entity = ShoppingListEntity::class,
        parentColumns = ["id"],
        childColumns = ["listId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["listId"])]
)
@TypeConverters(ProductMapper::class)
data class ProductEntity(
    @PrimaryKey
    var id: Int,
    var listId: Int,
    var name: String,
    var isSelected: Boolean = false
)