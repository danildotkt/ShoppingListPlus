package io.avdev.shoppinglistplus.ui.renamelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.usecase.list.RenameListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RenameListViewModel @AssistedInject constructor(private val  renameListUseCase : RenameListUseCase) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create() : RenameListViewModel
    }

    companion object {
        fun provideRenameListViewModel(factory: Factory) : ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create() as T
                }
            }
        }
    }

    fun renameList(newName : String, list : ShoppingList) {
        viewModelScope.launch(Dispatchers.Default) {
            list.name = newName
            if(newName == "") {
                list.name = "Новый список покупок +"
            }
            renameListUseCase.execute(list.id, list.name)
        }
    }
}
