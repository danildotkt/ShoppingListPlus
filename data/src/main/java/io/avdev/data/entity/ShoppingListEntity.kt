package io.avdev.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import io.avdev.data.mapper.ShoppingListMapper


@Entity(tableName = "lists")
@TypeConverters(ShoppingListMapper::class)
data class ShoppingListEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    var name : String,
    var itemList: List<ShoppingItemEntity>? = null
)