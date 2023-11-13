package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.avdev.domain.usecase.item.AddItemUseCase
import io.avdev.domain.usecase.item.SelectItemsUseCase
import io.avdev.domain.usecase.list.AddListUseCase
import io.avdev.domain.usecase.list.DeleteListUseCase
import io.avdev.domain.usecase.list.SelectListsUseCase
import io.avdev.shoppinglistplus.service.ShoppingItemService
import io.avdev.shoppinglistplus.service.ShoppingListService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    fun provideShoppingItemService
        (addItemUseCase: AddItemUseCase,
         selectItemsUseCase: SelectItemsUseCase) : ShoppingItemService {
        return ShoppingItemService(addItemUseCase, selectItemsUseCase)
    }
    @Provides
    @Singleton
    fun provideShoppingListService
        (addListUseCase: AddListUseCase,
         deleteListUseCase: DeleteListUseCase,
         selectListsUseCase: SelectListsUseCase) : ShoppingListService {
        return ShoppingListService(addListUseCase, deleteListUseCase, selectListsUseCase)
    }
}