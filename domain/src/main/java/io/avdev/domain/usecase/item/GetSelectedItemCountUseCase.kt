package io.avdev.domain.usecase.item

import io.avdev.domain.repository.ShoppingItemRepository
import kotlinx.coroutines.flow.Flow

class GetSelectedItemCountUseCase(private val repository : ShoppingItemRepository) {

    fun execute(id : Int) : Flow<Int> {
        return repository.getSelectedItemCount(id)
    }
}