package io.avdev.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.avdev.data.entity.ShoppingItemEntity

@Dao
interface ShoppingItemDao {

    @Insert
    fun insertItem(item: ShoppingItemEntity)

    @Query("Select * from items where listId = :id")
    fun selectItems(id: Int): List<ShoppingItemEntity>
}