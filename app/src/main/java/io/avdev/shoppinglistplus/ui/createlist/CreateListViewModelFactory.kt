package io.avdev.shoppinglistplus.ui.createlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.avdev.domain.usecase.list.CreateListUseCase

class CreateListViewModelFactory(val createListUseCase: CreateListUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateListViewModel(createListUseCase) as T
    }
}