package com.oblessing.filteringmatches.di

import com.oblessing.filteringmatches.network.WebService
import com.oblessing.filteringmatches.network.mappers.MatchEntityMapper
import com.oblessing.filteringmatches.repository.MatchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(webService: WebService, mapper: MatchEntityMapper): MatchRepository {
        return MatchRepository(webService, mapper)
    }
}