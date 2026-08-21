package com.ehealthinformatics.prognocare.di

import android.content.Context
import com.ehealthinformatics.prognocare.data.config.AppConfigStore
import com.ehealthinformatics.prognocare.data.remote.ApiBundle
import com.ehealthinformatics.prognocare.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAppConfigStore(@ApplicationContext context: Context): AppConfigStore =
        AppConfigStore(context)

    @Provides
    @Singleton
    fun provideApiBundle(client: RetrofitClient): StateFlow<ApiBundle> = client.apis
}