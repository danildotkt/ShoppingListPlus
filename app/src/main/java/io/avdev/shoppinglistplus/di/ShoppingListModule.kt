package io.avdev.shoppinglistplus.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.avdev.data.dao.ShoppingListDao
import io.avdev.data.database.ShoppingListDb
import io.avdev.data.mapper.ShoppingListMapper
import io.avdev.data.repository.ShoppingListRepositoryImpl
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
    fun provideShoppingListDao(@ApplicationContext context : Context): ShoppingListDao {
        val database = ShoppingListDb.getDataBase(context)
        return database.getShoppingListDao()
    }
    @Provides
    @Singleton
    fun provideShoppingListMapper() : ShoppingListMapper {
        return ShoppingListMapper()
    }
    @Provides
    @Singleton
    fun provideShoppingListDb(@ApplicationContext context: Context) : ShoppingListDb {
        return ShoppingListDb.getDataBase(context)
    }

}
























