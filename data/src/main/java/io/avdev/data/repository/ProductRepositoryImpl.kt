package io.avdev.data.repository

import io.avdev.data.dao.ProductDao
import io.avdev.data.mapper.ProductMapper
import io.avdev.domain.model.Product
import io.avdev.domain.repository.ShoppingProductRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val dao: ProductDao,
    private val mapper: ProductMapper
) : ShoppingProductRepository {

    override suspend fun createProduct(item: Product) {
        val entity = mapper.mapToEntity(item)
        dao.insertProduct(entity)
    }

    override suspend fun getProductsByListId(id: Int): List<Product> {
        val entityList = dao.getProductsByListId(id)
        return mapper.mapToModels(entityList)
    }

    override suspend fun updateProductSelection(itemId: Int, isSelected: Boolean) {
        dao.updateProductSelection(itemId, isSelected)
    }

    override suspend fun clearSelectionByListId(id: Int) {
        dao.clearSelectionByListId(id)
    }

    override fun getSelectedProductCount(id: Int): Flow<Int> {
        return dao.getSelectedProductCount(id)
    }

    override fun getTotalProductCount(id: Int): Flow<Int> {
        return dao.getTotalProductCount(id)
    }
}