package io.avdev.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.avdev.data.mapper.ShoppingItemMapper
import io.avdev.data.mapper.ShoppingListMapper

@Entity(tableName = "items")
@TypeConverters(ShoppingItemMapper::class)
data class ShoppingItemEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var listId: Int,
    var name : String,
    var isSelected : Boolean = false
)