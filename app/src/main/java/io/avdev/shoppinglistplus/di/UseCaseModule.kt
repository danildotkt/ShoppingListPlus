package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.repository.ShoppingItemRepository
import io.avdev.domain.repository.ShoppingListRepository
import io.avdev.domain.usecase.item.AddItemUseCase
import io.avdev.domain.usecase.item.SelectItemsUseCase
import io.avdev.domain.usecase.list.AddListUseCase
import io.avdev.domain.usecase.list.DeleteListUseCase
import io.avdev.domain.usecase.list.SelectListsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    fun provideAddListUseCase(repository: ShoppingListRepository) : AddListUseCase {
        return AddListUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideSelectListsUseCase(repository: ShoppingListRepository): SelectListsUseCase {
        return SelectListsUseCase(repository)
    }
    @Provides
    fun provideDeleteListUseCase(repository: ShoppingListRepository) : DeleteListUseCase {
        return DeleteListUseCase(repository)
    }

    @Provides
    fun provideAddItemUseCase(repository: ShoppingItemRepository) : AddItemUseCase {
        return AddItemUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideSelectItemsUseCase(repository: ShoppingItemRepository) : SelectItemsUseCase {
        return SelectItemsUseCase(repository)
    }
}