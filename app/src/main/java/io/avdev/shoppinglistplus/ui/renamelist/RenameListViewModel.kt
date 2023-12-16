package io.avdev.shoppinglistplus.ui.renamelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.usecase.list.RenameListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RenameListViewModel(private val renameListUseCase: RenameListUseCase) : ViewModel() {

    fun renameList(newName: String, list: ShoppingList) {
        viewModelScope.launch(Dispatchers.Default) {
            list.name = newName
            if (newName.isBlank()) {
                list.name = "Новый список покупок +"
            }
            renameListUseCase.execute(list.id, list.name)
        }
    }
}
