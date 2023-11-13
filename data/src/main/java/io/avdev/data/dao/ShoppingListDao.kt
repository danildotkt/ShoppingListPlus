package io.avdev.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.avdev.data.entity.ShoppingListEntity

@Dao
interface ShoppingListDao {

    @Insert
    fun insertShoppingList(shoppingListEntity: ShoppingListEntity)

    @Query("Select * from lists")
    fun getShoppingLists() : List<ShoppingListEntity>

    @Delete
    fun deleteShoppingList(shoppingListEntity: ShoppingListEntity)
}