package io.avdev.shoppinglistplus.ui.createlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.usecase.list.CreateListUseCase
import io.avdev.shoppinglistplus.adapter.SListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateListViewModel @AssistedInject constructor(
    private val createListUseCase: CreateListUseCase,
    private val adapter : SListAdapter) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create() : CreateListViewModel
    }

    companion object {
        fun provideCreateListViewModel(factory : Factory) : ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create() as T
                }
            }
        }
    }

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
        if(name == "") {
            return "Список покупок + "
        }
        return name
    }

    private fun generateId(): Int {
        return (Integer.MIN_VALUE..Integer.MAX_VALUE).random()
    }
}