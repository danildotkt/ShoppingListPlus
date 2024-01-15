package io.avdev.shoppinglistplus.ui.products

import androidx.lifecycle.ViewModel
import io.avdev.domain.usecase.item.CreateProductUseCase
import io.avdev.domain.usecase.item.GetProductsByListIdUseCase
import io.avdev.domain.usecase.item.UpdateProductSelectionUseCase
import io.avdev.shoppinglistplus.adapter.ProductAdapter

class ProductsViewModel(
    private val getProductsByListIdUseCase: GetProductsByListIdUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val updateItemUseCase: UpdateProductSelectionUseCase
) : ViewModel() {

    fun provideProductAdapter(fragment: ProductsFragment): ProductAdapter {
        return ProductAdapter(
            getProductsByListIdUseCase,
            fragment,
            this,
            updateItemUseCase,
            createProductUseCase
        )
    }
}