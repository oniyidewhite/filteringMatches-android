package com.oblessing.filteringmatches.di

import com.oblessing.filteringmatches.core.support.AssistedViewModelFactory
import com.oblessing.filteringmatches.core.support.MavericksViewModelComponent
import com.oblessing.filteringmatches.core.support.ViewModelKey
import com.oblessing.filteringmatches.ui.MatchListViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MatchListViewModel::class)
    fun provideMatchListViewModelFactory(factory: MatchListViewModel.Factory): AssistedViewModelFactory<*, *>
}