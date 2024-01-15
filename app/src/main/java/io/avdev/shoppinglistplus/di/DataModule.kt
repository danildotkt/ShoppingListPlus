package io.avdev.shoppinglistplus.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.avdev.data.dao.ProductDao
import io.avdev.data.dao.ShoppingListDao
import io.avdev.data.database.ShoppingListDatabase
import io.avdev.data.mapper.ProductMapper
import io.avdev.data.mapper.ShoppingListMapper
import io.avdev.data.repository.ProductRepositoryImpl
import io.avdev.data.repository.ShoppingListRepositoryImpl
import io.avdev.domain.repository.ShoppingListRepository
import io.avdev.domain.repository.ShoppingProductRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideShoppingListRepository(
        dao: ShoppingListDao,
        mapper: ShoppingListMapper
    ): ShoppingListRepository =
        ShoppingListRepositoryImpl(dao, mapper)

    @Provides
    @Singleton
    fun provideShoppingListDao(database: ShoppingListDatabase): ShoppingListDao =
        database.getShoppingListDao()

    @Provides
    @Singleton
    fun provideShoppingListMapper(): ShoppingListMapper =
        ShoppingListMapper()

    @Provides
    @Singleton
    fun provideShoppingItemRepository(
        dao: ProductDao,
        mapper: ProductMapper
    ): ShoppingProductRepository =
        ProductRepositoryImpl(dao, mapper)

    @Provides
    @Singleton
    fun provideShoppingItemDao(database: ShoppingListDatabase): ProductDao =
        database.getShoppingItemDao()

    @Provides
    @Singleton
    fun provideShoppingItemMapper(): ProductMapper =
        ProductMapper()

    @Provides
    @Singleton
    fun provideShoppingListDb(@ApplicationContext context: Context): ShoppingListDatabase =
        ShoppingListDatabase.getDataBase(context)
}
























