package io.avdev.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.avdev.data.mapper.ShoppingListMapper


@Entity(tableName = "lists")
@TypeConverters(ShoppingListMapper::class)
data class ShoppingListEntity(
    @PrimaryKey
    var id: Int,
    var name: String,
)