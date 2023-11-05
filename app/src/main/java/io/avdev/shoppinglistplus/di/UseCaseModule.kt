package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.avdev.domain.repository.ShoppingListRepository
import io.avdev.domain.usecase.list.AddListUseCase
import io.avdev.domain.usecase.list.DeleteListUseCase
import io.avdev.domain.usecase.list.SelectListsUseCase
import io.avdev.domain.usecase.list.UpdateListUseCase
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
    fun provideUpdateListUseCase(repository: ShoppingListRepository) : UpdateListUseCase {
        return UpdateListUseCase(repository)
    }
}