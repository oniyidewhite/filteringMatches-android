package com.oblessing.filteringmatches.di

import com.apollographql.apollo.ApolloClient
import com.oblessing.filteringmatches.BuildConfig
import com.oblessing.filteringmatches.core.debug
import com.oblessing.filteringmatches.network.graphql.GraphqlWebService
import com.oblessing.filteringmatches.network.WebService
import com.oblessing.filteringmatches.network.graphql.mappers.GraphqlEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.builder().serverUrl(BuildConfig.BASE_URL)
            .okHttpClient(OkHttpClient.Builder().apply {
                connectTimeout(15, TimeUnit.SECONDS)

                debug {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    addNetworkInterceptor(logging)
                }
            }.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideGraphqlMapper(): GraphqlEntityMapper {
        return GraphqlEntityMapper()
    }

    @Singleton
    @Provides
    fun provideWebService(apolloClient: ApolloClient, mapper: GraphqlEntityMapper): WebService {
        return GraphqlWebService(apolloClient, mapper)
    }
}