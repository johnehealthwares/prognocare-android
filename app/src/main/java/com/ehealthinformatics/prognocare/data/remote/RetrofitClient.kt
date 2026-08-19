package com.ehealthinformatics.prognocare.data.remote

import com.ehealthinformatics.prognocare.data.remote.api.AppointmentApi
import com.ehealthinformatics.prognocare.data.remote.api.DashboardApi
import com.ehealthinformatics.prognocare.data.remote.api.EncounterApi
import com.ehealthinformatics.prognocare.data.remote.api.FormApi
import com.ehealthinformatics.prognocare.data.remote.api.PatientApi
import com.ehealthinformatics.prognocare.data.remote.api.RequestApi
import com.ehealthinformatics.prognocare.data.remote.api.StaffApi
import com.ehealthinformatics.prognocare.data.remote.api.VisitApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitClient @Inject constructor(
    private val authInterceptor: AuthInterceptor,
) {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        isLenient = true
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val patientApi: PatientApi by lazy { retrofit.create(PatientApi::class.java) }
    val staffApi: StaffApi by lazy { retrofit.create(StaffApi::class.java) }
    val appointmentApi: AppointmentApi by lazy { retrofit.create(AppointmentApi::class.java) }
    val visitApi: VisitApi by lazy { retrofit.create(VisitApi::class.java) }
    val encounterApi: EncounterApi by lazy { retrofit.create(EncounterApi::class.java) }
    val requestApi: RequestApi by lazy { retrofit.create(RequestApi::class.java) }
    val formApi: FormApi by lazy { retrofit.create(FormApi::class.java) }
    val dashboardApi: DashboardApi by lazy { retrofit.create(DashboardApi::class.java) }

    companion object {
        // TODO: Move to BuildConfig or remote config
        var BASE_URL = "http://10.0.2.2:3000/"  // Android emulator localhost
    }
}
