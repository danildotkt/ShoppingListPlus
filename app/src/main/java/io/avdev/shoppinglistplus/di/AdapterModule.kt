package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import io.avdev.domain.usecase.item.AddItemUseCase
import io.avdev.domain.usecase.item.SelectItemsUseCase
import io.avdev.domain.usecase.list.DeleteListUseCase
import io.avdev.domain.usecase.list.SelectListsUseCase
import io.avdev.shoppinglistplus.adapter.ItemAdapter
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import io.avdev.shoppinglistplus.service.ShoppingItemService
import io.avdev.shoppinglistplus.service.ShoppingListService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Provides
    @Singleton
    fun provideShoppingListAdapter(shoppingListService : ShoppingListService): ShoppingListAdapter {
        return ShoppingListAdapter(shoppingListService)
    }
    @Provides
    @Singleton
    fun provideItemAdapter(shoppingItemService : ShoppingItemService): ItemAdapter {
        return ItemAdapter(shoppingItemService)
    }
}