package io.avdev.shoppinglistplus.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.avdev.shoppinglistplus.adapter.SListAdapter


class MainViewModel @AssistedInject constructor(private val SListAdapter: SListAdapter): ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create() : MainViewModel
    }

    companion object {
        fun provideMainViewModel(factory : Factory) : ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create() as T
                }
            }
        }
    }

    fun provideListAdapter() : SListAdapter {
        return SListAdapter
    }
}