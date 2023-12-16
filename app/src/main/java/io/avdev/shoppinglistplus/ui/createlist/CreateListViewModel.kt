package io.avdev.shoppinglistplus.ui.createlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.usecase.list.CreateListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateListViewModel(private val createListUseCase: CreateListUseCase) : ViewModel() {


    fun createShoppingList(name : String) {
        val newName = nameValidate(name)
        val shoppingList = ShoppingList(id = generateId(), name = newName)
        viewModelScope.launch(Dispatchers.Default) {
            try {
                createListUseCase.execute(shoppingList)
            } catch (e: Exception) {
                val coincidenceShoppingList = ShoppingList(id = generateId(), name = newName)
                createListUseCase.execute(coincidenceShoppingList)
            }
        }
    }

    private fun nameValidate(name : String) : String{
        if(name.isBlank()) {
            return "Список покупок + "
        }
        return name
    }

    private fun generateId(): Int {
        return (Integer.MIN_VALUE..Integer.MAX_VALUE).random()
    }
}