package io.avdev.domain.repository

import io.avdev.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ShoppingProductRepository {
    suspend fun createProduct(item: Product)
    suspend fun updateProductSelection(itemId: Int, isSelected: Boolean)
    suspend fun clearSelectionByListId(id: Int)
    suspend fun getProductsByListId(id: Int): List<Product>
    fun getSelectedProductCount(id: Int): Flow<Int>
    fun getTotalProductCount(id: Int): Flow<Int>
}