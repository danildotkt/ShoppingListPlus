package io.avdev.domain.usecase.item

import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.repository.ShoppingItemRepository
import kotlinx.coroutines.flow.Flow

class GetItemsByListIdUseCase(private val repository : ShoppingItemRepository) {

    fun execute(id: Int): Flow<List<ShoppingItem>> {
        return repository.getItemsByListId(id)
    }
}