package com.prashant.naik.ezcart.di

import com.prashant.naik.ezcart.network.DiscordApi
import com.prashant.naik.ezcart.network.ShoppingApi
import com.prashant.naik.ezcart.utils.Constants.Companion.BASE_URL
import com.prashant.naik.ezcart.utils.Constants.Companion.DISCORD_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    @Named("shopping")
    fun provideRetrofitInstance(okhttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("discord")
    fun provideDiscordRetrofitInstance(okhttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DISCORD_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesShoppingApi(@Named("shopping") retrofit: Retrofit): ShoppingApi = retrofit.create(ShoppingApi::class.java)

    @Singleton
    @Provides
    fun providesDiscordApi(@Named("discord") retrofit: Retrofit): DiscordApi = retrofit.create(DiscordApi::class.java)

}