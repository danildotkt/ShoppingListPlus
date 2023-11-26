package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
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
}