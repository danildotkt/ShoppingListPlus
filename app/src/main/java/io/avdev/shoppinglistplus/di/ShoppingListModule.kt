package io.avdev.shoppinglistplus.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.avdev.data.dao.ShoppingItemDao
import io.avdev.data.dao.ShoppingListDao
import io.avdev.data.database.ShoppingListDb
import io.avdev.data.mapper.ShoppingItemMapper
import io.avdev.data.mapper.ShoppingListMapper
import io.avdev.data.repository.ShoppingItemRepositoryImpl
import io.avdev.data.repository.ShoppingListRepositoryImpl
import io.avdev.domain.repository.ShoppingItemRepository
import io.avdev.domain.repository.ShoppingListRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ShoppingListModule {

    @Provides
    @Singleton
    fun provideShoppingListRepository(dao : ShoppingListDao, mapper : ShoppingListMapper) : ShoppingListRepository {
        return ShoppingListRepositoryImpl(dao, mapper)
    }
    @Provides
    @Singleton
    fun provideShoppingListDao(database : ShoppingListDb): ShoppingListDao {
        return database.getShoppingListDao()
    }
    @Provides
    @Singleton
    fun provideShoppingListMapper() : ShoppingListMapper {
        return ShoppingListMapper()
    }

    @Provides
    @Singleton
    fun provideShoppingItemRepository(dao: ShoppingItemDao, mapper: ShoppingItemMapper) : ShoppingItemRepository {
        return ShoppingItemRepositoryImpl(dao, mapper)
    }
    @Provides
    @Singleton
    fun provideShoppingItemDao(database: ShoppingListDb): ShoppingItemDao {
        return database.getShoppingItemDao()
    }
    @Provides
    @Singleton
    fun provideShoppingItemMapper() : ShoppingItemMapper {
        return ShoppingItemMapper()
    }

    @Provides
    @Singleton
    fun provideShoppingListDb(@ApplicationContext context: Context) : ShoppingListDb {
        return ShoppingListDb.getDataBase(context)
    }

}
























