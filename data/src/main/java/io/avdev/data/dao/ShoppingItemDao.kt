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

    @Query("SELECT * FROM items WHERE id = :id")
    fun selectItems(id: Int): List<ShoppingItemEntity>

    @Update
    fun updateItem(item : ShoppingItemEntity)

    @Delete
    fun deleteItem(item: ShoppingItemEntity)

    @Query("UPDATE items SET isSelected = 1 WHERE id = :itemId")
    fun setListSelected(itemId: Int)

    @Query("UPDATE items SET isSelected = 0 WHERE id = :itemId")
    fun setListUnselected(itemId: Int)
}