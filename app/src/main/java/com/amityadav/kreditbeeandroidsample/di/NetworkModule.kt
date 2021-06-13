package com.amityadav.kreditbeeandroidsample.di

import com.amityadav.kreditbeeandroidsample.BuildConfig
import com.amityadav.kreditbeeandroidsample.data.network.ApiService
import com.amityadav.kreditbeeandroidsample.data.network.NetworkManagerImpl
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 30L

val NetworkModule = module {

    single { createService(get()) }

    single { createRetrofit(get(), BuildConfig.BASE_URL) }

    single { MoshiConverterFactory.create() }

    single { Moshi.Builder().build() }

    single { createOkHttpClient() }

}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create()).build()
}

fun createService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun createNetworkManager(apiService: ApiService): NetworkManagerImpl {
    return NetworkManagerImpl(apiService)
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build()
}

