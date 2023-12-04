package io.avdev.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.avdev.data.mapper.ShoppingItemMapper

@Entity(tableName = "items",
    foreignKeys = [ForeignKey(entity = ShoppingListEntity::class,
        parentColumns = ["id"],
        childColumns = ["listId"],
        onDelete = ForeignKey.CASCADE)])
@TypeConverters(ShoppingItemMapper::class)
data class ShoppingItemEntity (
    @PrimaryKey
    var id : Int,
    var listId: Int,
    var name : String,
    var isSelected : Boolean = false
)