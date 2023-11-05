package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.avdev.domain.usecase.list.DeleteListUseCase
import io.avdev.domain.usecase.list.SelectListsUseCase
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Provides
    @Singleton
    fun provideShoppingListAdapter(useCase: SelectListsUseCase, useCase1: DeleteListUseCase): ShoppingListAdapter {
        return ShoppingListAdapter(useCase, useCase1)
    }

}