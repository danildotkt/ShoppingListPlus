package io.avdev.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.avdev.data.mapper.ShoppingItemMapper
import io.avdev.data.mapper.ShoppingListMapper

@Entity(tableName = "items",
    foreignKeys = [ForeignKey(entity = ShoppingListEntity::class,
        parentColumns = ["id"],
        childColumns = ["listId"],
        onDelete = ForeignKey.CASCADE)])
@TypeConverters(ShoppingItemMapper::class)
data class ShoppingItemEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    var listId: Int? = null,
    var name : String,
    var isSelected : Boolean = false
)