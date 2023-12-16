package io.avdev.shoppinglistplus.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.avdev.domain.usecase.item.CreateProductUseCase
import io.avdev.domain.usecase.item.GetProductsByListIdUseCase
import io.avdev.domain.usecase.item.UpdateProductSelectionUseCase

class ProductsViewModelFactory(
    private val getProductsByListIdUseCase: GetProductsByListIdUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val updateItemUseCase: UpdateProductSelectionUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsViewModel(getProductsByListIdUseCase, createProductUseCase, updateItemUseCase) as T
    }
}