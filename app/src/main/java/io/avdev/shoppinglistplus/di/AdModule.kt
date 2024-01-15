package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.avdev.shoppinglistplus.ad.YandexBanner
import io.avdev.shoppinglistplus.ad.YandexInterstitialAd

@Module
@InstallIn(SingletonComponent::class)
object AdModule {

    @Provides
    fun provideYandexBanner(): YandexBanner = YandexBanner()

    @Provides
    fun provideYandexInterstitial(): YandexInterstitialAd =
        YandexInterstitialAd()
}