package com.lobster.usb.presentation.di

import com.lobster.usb.BuildConfig
import com.lobster.usb.data.IexApi
import com.lobster.usb.data.LocalRepository
import com.lobster.usb.data.RemoteRepository
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton
import java.util.concurrent.TimeUnit

/**
 * Created by Lobster on 27/10/18.
 */
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideHttpBuilder(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .baseUrl(BuildConfig.SERVER)
        .build()

    @Provides
    @Singleton
    fun provideIexApi(retrofit: Retrofit) = retrofit.create<IexApi>(IexApi::class.java)

    @Provides
    @Singleton
    fun provideLocalRepository(boxStore: BoxStore) = LocalRepository(boxStore)

    @Provides
    @Singleton
    fun provideRemoteRepository(api: IexApi) = RemoteRepository(api)

}
