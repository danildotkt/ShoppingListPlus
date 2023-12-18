package io.avdev.shoppinglistplus.ui.shoppinglist

import ShoppingListViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.avdev.shoppinglistplus.ad.YandexBanner
import io.avdev.shoppinglistplus.ad.YandexInterstitialAd
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter

class ShoppingListViewModelFactory(
    private val banner: YandexBanner,
    private val interstitialAd: YandexInterstitialAd,
    val adapter: ShoppingListAdapter
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShoppingListViewModel(banner, interstitialAd, adapter) as T
    }
}