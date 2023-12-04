package io.avdev.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.avdev.data.entity.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {

    @Insert
    fun insertItem(item: ShoppingItemEntity)

    @Query("Select * from items where listId = :id")
    fun getItemsByListId(id : Int): List<ShoppingItemEntity>

    @Query("Update items set isSelected = :isSelected where id = :itemId")
    fun updateItemSelection(itemId : Int, isSelected : Boolean)

    @Query("Update items set isSelected = 0 where listId = :id")
    fun clearSelectionByListId(id: Int)

    @Query("Select COUNT(*) from items where listId = :id and isSelected = 1")
    fun getSelectedItemCount(id: Int): Flow<Int>

    @Query("Select COUNT(*) from items where listId = :id")
    fun getTotalItemCount(id: Int): Flow<Int>
}