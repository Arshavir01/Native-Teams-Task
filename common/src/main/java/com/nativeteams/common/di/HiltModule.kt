package com.nativeteams.common.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nativeteams.common.data.network.NetworkApiService
import com.nativeteams.common.data.repository.StocksStocksRepositoryImpl
import com.nativeteams.common.domain.repository.StocksRepository
import com.nativeteams.common.domain.useCase.GetStocksDataUseCase
import com.nativeteams.common.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder().build()
    }
    @Provides
    @Singleton
    fun provideGson(): Gson{
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideNetworkApiService(gson: Gson, client: OkHttpClient): NetworkApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(NetworkApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: NetworkApiService): StocksRepository{
        return StocksStocksRepositoryImpl(apiService)
    }

}