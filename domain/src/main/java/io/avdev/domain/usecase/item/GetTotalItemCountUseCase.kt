package io.avdev.domain.usecase.item

import io.avdev.domain.repository.ShoppingItemRepository
import kotlinx.coroutines.flow.Flow

class GetTotalItemCountUseCase(private val repository : ShoppingItemRepository) {

    fun execute(id : Int) : Flow<Int> {
        return repository.getTotalItemCount(id)
    }
}