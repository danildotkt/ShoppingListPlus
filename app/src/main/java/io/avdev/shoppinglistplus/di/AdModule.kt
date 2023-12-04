package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.avdev.shoppinglistplus.ad.YandexBanner
import io.avdev.shoppinglistplus.ad.YandexInterstitial

@Module
@InstallIn(ActivityComponent::class)
object AdModule {

    @Provides
    fun provideYandexBanner() : YandexBanner {
        return YandexBanner()
    }

    @Provides
    fun provideYandexInterstitial() : YandexInterstitial {
        return YandexInterstitial()
    }
}