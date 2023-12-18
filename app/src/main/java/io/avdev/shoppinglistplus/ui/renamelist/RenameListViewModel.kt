package io.avdev.shoppinglistplus.ui.renamelist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.usecase.list.RenameListUseCase
import io.avdev.shoppinglistplus.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RenameListViewModel(
    private val renameListUseCase: RenameListUseCase,
) : ViewModel() {

    fun renameList(newName: String, list: ShoppingList, context: Context) {
        viewModelScope.launch(Dispatchers.Default) {
            list.name = newName
            if (newName.isBlank()) {
                list.name =  context.getString(R.string.new_shopping_list_plus)

            }
            renameListUseCase.execute(list.id, list.name)
        }
    }
}
