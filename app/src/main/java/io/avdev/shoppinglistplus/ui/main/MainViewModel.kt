package io.avdev.shoppinglistplus.ui.main

import androidx.lifecycle.ViewModel
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter


class MainViewModel(private val shoppingListAdapter: ShoppingListAdapter) : ViewModel() {

    fun provideListAdapter(): ShoppingListAdapter {
        return shoppingListAdapter
    }
}