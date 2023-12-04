package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.avdev.domain.repository.ShoppingItemRepository
import io.avdev.domain.repository.ShoppingListRepository
import io.avdev.domain.usecase.item.ClearSelectionUseCase
import io.avdev.domain.usecase.item.CreateItemUseCase
import io.avdev.domain.usecase.item.GetItemsByListIdUseCase
import io.avdev.domain.usecase.item.GetSelectedItemCountUseCase
import io.avdev.domain.usecase.item.GetTotalItemCountUseCase
import io.avdev.domain.usecase.item.UpdateItemSelectionUseCase
import io.avdev.domain.usecase.list.CreateListUseCase
import io.avdev.domain.usecase.list.DeleteListUseCase
import io.avdev.domain.usecase.list.GetListsUseCase
import io.avdev.domain.usecase.list.RenameListUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCreateListUseCase(repository: ShoppingListRepository) : CreateListUseCase {
        return CreateListUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideGetListsUseCase(repository: ShoppingListRepository): GetListsUseCase {
        return GetListsUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideDeleteListUseCase(repository: ShoppingListRepository) : DeleteListUseCase {
        return DeleteListUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideRenameListUseCase(repository: ShoppingListRepository) : RenameListUseCase {
        return RenameListUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideCreateItemUseCase(repository: ShoppingItemRepository) : CreateItemUseCase {
        return CreateItemUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideGetItemsByListIdUseCase(repository: ShoppingItemRepository) : GetItemsByListIdUseCase {
        return GetItemsByListIdUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideUpdateItemSelectionUseCase(repository: ShoppingItemRepository) : UpdateItemSelectionUseCase {
        return UpdateItemSelectionUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideClearSelectionUseCase(repository: ShoppingItemRepository) : ClearSelectionUseCase {
        return ClearSelectionUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideGetSelectedItemCountUseCase(repository: ShoppingItemRepository) : GetSelectedItemCountUseCase {
        return GetSelectedItemCountUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideGetTotalItemCountUseCase(repository: ShoppingItemRepository) : GetTotalItemCountUseCase {
        return GetTotalItemCountUseCase(repository)
    }

}