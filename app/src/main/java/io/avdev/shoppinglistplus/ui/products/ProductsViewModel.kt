package io.avdev.shoppinglistplus.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.avdev.domain.usecase.item.CreateItemUseCase
import io.avdev.domain.usecase.item.GetItemsByListIdUseCase
import io.avdev.domain.usecase.item.UpdateItemSelectionUseCase
import io.avdev.shoppinglistplus.adapter.ItemAdapter

class ProductsViewModel @AssistedInject constructor(
    private val getItemsByListIdUseCase: GetItemsByListIdUseCase,
    private val createItemUseCase: CreateItemUseCase,
    private val updateItemUseCase: UpdateItemSelectionUseCase): ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create() : ProductsViewModel
    }

    companion object {
        fun providesProductsViewModel(factory : Factory) : ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create() as T
                }
            }
        }
    }

    fun provideItemAdapter(fragment : ProductsFragment) : ItemAdapter{
        return ItemAdapter(getItemsByListIdUseCase, fragment, updateItemUseCase, createItemUseCase)
    }
}