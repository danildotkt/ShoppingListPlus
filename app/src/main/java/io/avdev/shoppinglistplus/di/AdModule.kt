package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.avdev.shoppinglistplus.ad.YandexBanner

@Module
@InstallIn(ActivityComponent::class)
class AdModule {
    @Provides
    fun provideYandexBanner() : YandexBanner {
        return YandexBanner()
    }
}