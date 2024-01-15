package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.avdev.domain.usecase.item.ClearSelectionUseCase
import io.avdev.domain.usecase.item.GetProductsByListIdUseCase
import io.avdev.domain.usecase.item.GetSelectedProductCountUseCase
import io.avdev.domain.usecase.item.GetTotalProductCountUseCase
import io.avdev.domain.usecase.list.DeleteListUseCase
import io.avdev.domain.usecase.list.GetListsUseCase
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {
    @Provides
    @Singleton
    fun provideListAdapter(
        getListUseCase: GetListsUseCase,
        getSelectedProductCountUseCase: GetSelectedProductCountUseCase,
        getTotalProductCountUseCase: GetTotalProductCountUseCase,
        deleteListUseCase: DeleteListUseCase,
        clearSelectionUseCase: ClearSelectionUseCase,
        getProductsByListIdUseCase: GetProductsByListIdUseCase
    ): ShoppingListAdapter {
        return ShoppingListAdapter(
            getListUseCase,
            getSelectedProductCountUseCase,
            getTotalProductCountUseCase,
            deleteListUseCase,
            clearSelectionUseCase,
            getProductsByListIdUseCase
        )
    }
}