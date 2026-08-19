package com.ehealthinformatics.prognocare.di

import com.ehealthinformatics.prognocare.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providePatientApi(client: RetrofitClient) = client.patientApi

    @Provides
    @Singleton
    fun provideStaffApi(client: RetrofitClient) = client.staffApi

    @Provides
    @Singleton
    fun provideAppointmentApi(client: RetrofitClient) = client.appointmentApi

    @Provides
    @Singleton
    fun provideVisitApi(client: RetrofitClient) = client.visitApi

    @Provides
    @Singleton
    fun provideEncounterApi(client: RetrofitClient) = client.encounterApi

    @Provides
    @Singleton
    fun provideRequestApi(client: RetrofitClient) = client.requestApi

    @Provides
    @Singleton
    fun provideFormApi(client: RetrofitClient) = client.formApi

    @Provides
    @Singleton
    fun provideDashboardApi(client: RetrofitClient) = client.dashboardApi
}
