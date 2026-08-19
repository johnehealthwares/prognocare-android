package com.ehealthinformatics.prognocare.data.remote.api

import com.ehealthinformatics.prognocare.data.remote.models.DashboardSummary
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DashboardApi {

    @GET("api/dashboard")
    suspend fun summary(
        @Query("date") date: String? = null,
    ): Response<DashboardSummary>
}
