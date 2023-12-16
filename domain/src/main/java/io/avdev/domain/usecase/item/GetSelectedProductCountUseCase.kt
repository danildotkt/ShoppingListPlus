package io.avdev.domain.usecase.item

import io.avdev.domain.repository.ShoppingProductRepository
import kotlinx.coroutines.flow.Flow

class GetSelectedProductCountUseCase(private val repository: ShoppingProductRepository) {

    fun execute(id: Int): Flow<Int> {
        return repository.getSelectedProductCount(id)
    }
}