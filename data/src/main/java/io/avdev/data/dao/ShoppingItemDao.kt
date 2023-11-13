package io.avdev.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.avdev.data.entity.ShoppingItemEntity
import io.avdev.data.entity.ShoppingListEntity

@Dao
interface ShoppingItemDao {

    @Insert
    fun insertItem(item: ShoppingItemEntity)

    @Query("SELECT * FROM items WHERE listId = :id")
    fun selectItems(id: Int): List<ShoppingItemEntity>
}