package io.avdev.shoppinglistplus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.avdev.domain.usecase.item.ClearSelectionUseCase
import io.avdev.domain.usecase.item.GetSelectedItemCountUseCase
import io.avdev.domain.usecase.item.GetTotalItemCountUseCase
import io.avdev.domain.usecase.list.DeleteListUseCase
import io.avdev.domain.usecase.list.GetListsUseCase
import io.avdev.shoppinglistplus.adapter.SListAdapter
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {
    @Provides
    @Singleton
    fun provideListAdapter(
        getListUseCase: GetListsUseCase,
        getSelectedItemCountUseCase: GetSelectedItemCountUseCase,
        getTotalItemCountUseCase: GetTotalItemCountUseCase,
        deleteListUseCase: DeleteListUseCase,
        clearSelectionUseCase: ClearSelectionUseCase) : SListAdapter {

        return SListAdapter(getListUseCase, getSelectedItemCountUseCase,
            getTotalItemCountUseCase, deleteListUseCase, clearSelectionUseCase)
    }
}