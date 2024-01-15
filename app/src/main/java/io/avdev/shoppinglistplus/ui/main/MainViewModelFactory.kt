package io.avdev.shoppinglistplus.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter

class MainViewModelFactory(private val shoppingListAdapter: ShoppingListAdapter) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(shoppingListAdapter) as T
    }
}