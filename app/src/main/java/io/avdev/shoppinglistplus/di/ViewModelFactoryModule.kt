package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.avdev.domain.usecase.item.CreateProductUseCase
import io.avdev.domain.usecase.item.GetProductsByListIdUseCase
import io.avdev.domain.usecase.item.UpdateProductSelectionUseCase
import io.avdev.domain.usecase.list.CreateListUseCase
import io.avdev.domain.usecase.list.RenameListUseCase
import io.avdev.shoppinglistplus.ad.YandexBanner
import io.avdev.shoppinglistplus.ad.YandexInterstitialAd
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import io.avdev.shoppinglistplus.ui.createlist.CreateListViewModelFactory
import io.avdev.shoppinglistplus.ui.main.MainViewModelFactory
import io.avdev.shoppinglistplus.ui.products.ProductsViewModelFactory
import io.avdev.shoppinglistplus.ui.renamelist.RenameListViewModelFactory
import io.avdev.shoppinglistplus.ui.shoppinglist.ShoppingListViewModelFactory

@Module
@InstallIn(ActivityComponent::class)
object ViewModelFactoryModule {

    @Provides
    fun provideMainViewModuleFactory(shoppingListAdapter: ShoppingListAdapter): MainViewModelFactory {
        return MainViewModelFactory(shoppingListAdapter)
    }

    @Provides
    fun provideCreateListViewModuleFactory(createListUseCase: CreateListUseCase): CreateListViewModelFactory {
        return CreateListViewModelFactory(createListUseCase)
    }

    @Provides
    fun provideProductsViewModelFactory(
        getProductsByListIdUseCase: GetProductsByListIdUseCase,
        createProductUseCase: CreateProductUseCase,
        updateItemUseCase: UpdateProductSelectionUseCase
    ): ProductsViewModelFactory {
        return ProductsViewModelFactory(
            getProductsByListIdUseCase,
            createProductUseCase,
            updateItemUseCase
        )
    }

    @Provides
    fun provideRenameListViewModelFactory(renameListUseCase: RenameListUseCase): RenameListViewModelFactory {
        return RenameListViewModelFactory(renameListUseCase)
    }

    @Provides
    fun provideShoppingListVIewModelFactory(
        banner: YandexBanner,
        interstitialAd: YandexInterstitialAd,
        adapter: ShoppingListAdapter
    ): ShoppingListViewModelFactory {
        return ShoppingListViewModelFactory(banner, interstitialAd, adapter)
    }

}













