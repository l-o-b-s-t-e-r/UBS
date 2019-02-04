package com.lobster.usb.presentation.di

import com.google.gson.GsonBuilder
import com.lobster.usb.BuildConfig
import com.lobster.usb.data.IexApi
import com.lobster.usb.data.LocalRepository
import com.lobster.usb.data.RemoteRepository
import com.lobster.usb.domain.adapters.SymbolTypeAdapter
import com.lobster.usb.domain.interfaces.ILocalRepository
import com.lobster.usb.domain.interfaces.IRemoteRepository
import com.lobster.usb.domain.wrappers.SymbolsWrapper
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


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
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(SymbolsWrapper::class.java, SymbolTypeAdapter())
            .create()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .baseUrl(BuildConfig.SERVER)
            .build()
    }

    @Provides
    @Singleton
    fun provideIexApi(retrofit: Retrofit) = retrofit.create<IexApi>(IexApi::class.java)

    @Provides
    @Singleton
    fun provideLocalRepository(boxStore: BoxStore): ILocalRepository = LocalRepository(boxStore)

    @Provides
    @Singleton
    fun provideRemoteRepository(api: IexApi): IRemoteRepository = RemoteRepository(api)

}
