package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import io.avdev.shoppinglistplus.anim.ItemTouchHelperCallback
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AnimationModule {
    @Provides
    @Singleton
    fun provideItemTouchHelperCallback() : ItemTouchHelperCallback {
        return ItemTouchHelperCallback()
    }
}