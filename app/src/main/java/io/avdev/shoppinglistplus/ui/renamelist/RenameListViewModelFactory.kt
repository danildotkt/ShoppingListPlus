package io.avdev.shoppinglistplus.ui.renamelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.avdev.domain.usecase.list.RenameListUseCase

class RenameListViewModelFactory(private val renameListUseCase: RenameListUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RenameListViewModel(renameListUseCase) as T
    }
}