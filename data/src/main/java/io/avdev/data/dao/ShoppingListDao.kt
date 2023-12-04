package io.avdev.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.avdev.data.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {

    @Insert
    fun insertShoppingList(shoppingListEntity: ShoppingListEntity)

    @Query("Select * from lists")
    fun getShoppingLists() : Flow<List<ShoppingListEntity>>

    @Delete
    fun deleteShoppingList(shoppingListEntity: ShoppingListEntity)

    @Query("Update lists set name = :newName where id = :id")
    fun renameShoppingList(id : Int, newName : String)
}