package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.avdev.domain.repository.ShoppingListRepository
import io.avdev.domain.repository.ShoppingProductRepository
import io.avdev.domain.usecase.item.ClearSelectionUseCase
import io.avdev.domain.usecase.item.CreateProductUseCase
import io.avdev.domain.usecase.item.GetProductsByListIdUseCase
import io.avdev.domain.usecase.item.GetSelectedProductCountUseCase
import io.avdev.domain.usecase.item.GetTotalProductCountUseCase
import io.avdev.domain.usecase.item.UpdateProductSelectionUseCase
import io.avdev.domain.usecase.list.CreateListUseCase
import io.avdev.domain.usecase.list.DeleteListUseCase
import io.avdev.domain.usecase.list.GetListsUseCase
import io.avdev.domain.usecase.list.RenameListUseCase

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideCreateListUseCase(repository: ShoppingListRepository): CreateListUseCase {
        return CreateListUseCase(repository)
    }

    @Provides
    fun provideGetListsUseCase(repository: ShoppingListRepository): GetListsUseCase {
        return GetListsUseCase(repository)
    }

    @Provides
    fun provideDeleteListUseCase(repository: ShoppingListRepository): DeleteListUseCase {
        return DeleteListUseCase(repository)
    }

    @Provides
    fun provideRenameListUseCase(repository: ShoppingListRepository): RenameListUseCase {
        return RenameListUseCase(repository)
    }


    @Provides
    fun provideCreateProductUseCase(repository: ShoppingProductRepository): CreateProductUseCase {
        return CreateProductUseCase(repository)
    }

    @Provides
    fun provideGetProductsByListIdUseCase(repository: ShoppingProductRepository): GetProductsByListIdUseCase {
        return GetProductsByListIdUseCase(repository)
    }

    @Provides
    fun provideUpdateProductSelectionUseCase(repository: ShoppingProductRepository): UpdateProductSelectionUseCase {
        return UpdateProductSelectionUseCase(repository)
    }

    @Provides
    fun provideClearSelectionUseCase(repository: ShoppingProductRepository): ClearSelectionUseCase {
        return ClearSelectionUseCase(repository)
    }

    @Provides
    fun provideGetSelectedProductCountUseCase(repository: ShoppingProductRepository): GetSelectedProductCountUseCase {
        return GetSelectedProductCountUseCase(repository)
    }

    @Provides
    fun provideGetTotalProductCountUseCase(repository: ShoppingProductRepository): GetTotalProductCountUseCase {
        return GetTotalProductCountUseCase(repository)
    }

}