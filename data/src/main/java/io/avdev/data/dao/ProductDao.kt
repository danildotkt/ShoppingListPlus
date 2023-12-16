package io.avdev.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.avdev.data.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert
    fun insertProduct(item: ProductEntity)

    @Query("Select * from items where listId = :id")
    fun getProductsByListId(id: Int): List<ProductEntity>

    @Query("Update items set isSelected = :isSelected where id = :itemId")
    fun updateProductSelection(itemId: Int, isSelected: Boolean)

    @Query("Update items set isSelected = 0 where listId = :id")
    fun clearSelectionByListId(id: Int)

    @Query("Select COUNT(*) from items where listId = :id and isSelected = 1")
    fun getSelectedProductCount(id: Int): Flow<Int>

    @Query("Select COUNT(*) from items where listId = :id")
    fun getTotalProductCount(id: Int): Flow<Int>
}